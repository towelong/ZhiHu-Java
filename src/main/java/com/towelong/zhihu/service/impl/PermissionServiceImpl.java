/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 11:40
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.mapper.PermissionMapper;
import com.towelong.zhihu.model.PermissionDO;
import com.towelong.zhihu.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {

    @Override
    public Map<String, List<PermissionDO>> selectPermission() {
        List<PermissionDO> permissionList = this.baseMapper.selectList(null);
        return Optional.ofNullable(permissionList)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(PermissionDO::getModule));
    }
}
