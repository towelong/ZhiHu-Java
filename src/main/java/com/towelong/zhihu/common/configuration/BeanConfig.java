/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/17 9:00
 */
package com.towelong.zhihu.common.configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.towelong.zhihu.common.beans.RouteMetaCollector;
import com.towelong.zhihu.common.core.DoubleJWT;
import com.towelong.zhihu.common.core.SingleJWT;
import com.towelong.zhihu.model.PermissionDO;
import com.towelong.zhihu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BeanConfig {

    @Autowired
    private PermissionService permissionService;

    @Value("${token.access-expire}")
    private String accessExpire;

    @Value("${token.refresh-expire}")
    private String refreshExpire;

    @Value("${token.secret}")
    private String secret;

    @Bean
    public SingleJWT singleJWT(){
        return new SingleJWT(Long.parseLong(accessExpire),secret);
    }

    @Bean
    public DoubleJWT doubleJWT(){
        return new DoubleJWT(secret,Long.parseLong(accessExpire),Long.parseLong(refreshExpire));
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * 将RouteMeta信息存入数据库
     * @return RouteMetaCollector
     */
    @Bean
    public RouteMetaCollector postProcessBeans(){
        return new RouteMetaCollector(meta -> {
            if (meta.mount()){
                String module = meta.module();
                String permission = meta.permission();
                QueryWrapper<PermissionDO> wrapper = new QueryWrapper<>();
                wrapper.lambda().eq(PermissionDO::getName, permission).eq(PermissionDO::getModule, module);
                PermissionDO one = permissionService.getOne(wrapper);
                if (one == null) {
                    permissionService.save(PermissionDO.builder().module(module).name(permission).build());
                }
            }
        });
    }



}
