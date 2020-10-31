/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 18:52
 */
package com.towelong.zhihu.controller.v1;

import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.common.ResultResponse;
import com.towelong.zhihu.common.UnifyResponse;
import com.towelong.zhihu.common.annotation.LoginRequired;
import com.towelong.zhihu.common.annotation.RefreshRequired;
import com.towelong.zhihu.common.configuration.CodeMessageConfiguration;
import com.towelong.zhihu.common.core.DoubleJWT;
import com.towelong.zhihu.common.exception.ExistException;
import com.towelong.zhihu.dto.user.ChangePasswordDTO;
import com.towelong.zhihu.dto.user.CreateOrUpdateUserDTO;
import com.towelong.zhihu.dto.user.UserLoginDTO;
import com.towelong.zhihu.model.UserDO;
import com.towelong.zhihu.service.UserService;
import com.towelong.zhihu.vo.TokenVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CodeMessageConfiguration code;

    @Autowired
    private DoubleJWT doubleJWT;

    @PostMapping("")
    public UnifyResponse createUser(@RequestBody @Validated CreateOrUpdateUserDTO validate) {
        if (userService.createUser(validate)) {
            return new UnifyResponse(20, code.getMessage(20));
        }
        return new UnifyResponse(30000, code.getMessage(30000));
    }

    @PostMapping("/login")
    public ResultResponse<TokenVo> loginUser(@RequestBody @Validated UserLoginDTO validate) {
        UserDO user = userService.userLogin(validate);
        return new ResultResponse<>(
                0,
                doubleJWT.generateTokens(user.getId()),
                "ok");
    }

    @PutMapping("")
    @LoginRequired
    public UnifyResponse updateUserPassword(@RequestBody @Validated ChangePasswordDTO validate) {
        userService.updateUserPassword(validate);
        return new UnifyResponse(21, code.getMessage(21));
    }

    @GetMapping("/refresh")
    @RefreshRequired
    public ResultResponse<TokenVo> refreshToken() {
        UserDO user = LocalUser.getLocalUser();
        return new ResultResponse<>(
                0,
                doubleJWT.generateTokens(user.getId()),
                "ok");
    }
}
