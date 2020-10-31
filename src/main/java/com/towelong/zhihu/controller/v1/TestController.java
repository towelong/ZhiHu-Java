/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/13 12:11
 */
package com.towelong.zhihu.controller.v1;


import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.common.annotation.RouteMeta;
import com.towelong.zhihu.common.annotation.UserPermissionRequired;
import com.towelong.zhihu.common.exception.ExistException;
import com.towelong.zhihu.model.UserDO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
public class TestController {


    @GetMapping("")
    @UserPermissionRequired
    @RouteMeta(permission = "删除hello", module = "hello", mount = true)
    public UserDO Hello() {
        return LocalUser.getLocalUser();
    }

    @GetMapping("/exception")
    public void Hello2() {
        throw new ExistException(9999);
    }

    @GetMapping("/h")
    @UserPermissionRequired
    @RouteMeta(permission = "访问h",module = "h",mount = true)
    public String h(){
        return "h";
    }
}
