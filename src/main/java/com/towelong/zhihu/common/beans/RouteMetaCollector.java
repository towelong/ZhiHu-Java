/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/22 17:42
 */
package com.towelong.zhihu.common.beans;

import com.towelong.zhihu.common.annotation.RouteMeta;
import com.towelong.zhihu.common.interfaces.MetaPreHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RouteMetaCollector implements BeanPostProcessor {

    private MetaPreHandler preHandler;

    public RouteMetaCollector() {
    }

    public RouteMetaCollector(MetaPreHandler preHandler) {
        this.preHandler = preHandler;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        for (Method method : methods) {
            RouteMeta meta = AnnotationUtils.findAnnotation(method, RouteMeta.class);
            if (meta != null) {
                if (preHandler != null) {
                    // routeMeta前置处理，将权限添加到数据库
                    preHandler.handle(meta);
                }
            }
        }
        return bean;
    }

}
