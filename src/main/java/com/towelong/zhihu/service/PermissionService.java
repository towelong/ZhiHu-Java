/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 13:32
 */
package com.towelong.zhihu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.towelong.zhihu.model.PermissionDO;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<PermissionDO> {
    /*
     * CMS端API
     */

    /**
     * 查询所有的Permission按module分类
     */
    Map<String, List<PermissionDO>> selectPermission();

}
