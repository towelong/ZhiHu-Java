/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 18:55
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.mapper.UserPermissionMapper;
import com.towelong.zhihu.model.UserPermissionDO;
import com.towelong.zhihu.service.UserPermissionService;
import org.springframework.stereotype.Service;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermissionDO> implements UserPermissionService {

}
