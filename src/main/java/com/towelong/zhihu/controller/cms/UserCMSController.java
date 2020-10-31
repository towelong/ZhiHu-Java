/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/10/4 18:05
 */
package com.towelong.zhihu.controller.cms;

import com.towelong.zhihu.model.PermissionDO;
import com.towelong.zhihu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cms/user")
@Validated
public class UserCMSController {

    @Autowired
    private PermissionService permissionService;

//    @GetMapping("/")
//    public void getUserPermission(){
//
//    }

    @GetMapping("/permission")
    public Map<String, List<PermissionDO>> getAllPermission(){
        return permissionService.selectPermission();
    }
}
