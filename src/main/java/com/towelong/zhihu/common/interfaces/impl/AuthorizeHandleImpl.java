/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/24 13:07
 */
package com.towelong.zhihu.common.interfaces.impl;

import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.common.annotation.RouteMeta;
import com.towelong.zhihu.common.core.DoubleJWT;
import com.towelong.zhihu.common.exception.AuthorizationException;
import com.towelong.zhihu.common.exception.NotFoundException;
import com.towelong.zhihu.common.interfaces.AuthorizeHandler;
import com.towelong.zhihu.model.PermissionDO;
import com.towelong.zhihu.model.UserDO;
import com.towelong.zhihu.model.UserPermissionDO;
import com.towelong.zhihu.service.PermissionService;
import com.towelong.zhihu.service.UserPermissionService;
import com.towelong.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthorizeHandleImpl implements AuthorizeHandler {

    @Autowired
    private DoubleJWT jwt;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserPermissionService userPermissionService;

    @Override
    public boolean handleLogin(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        String token = verifyHeader(request, response);
        Map<String, Claim> claims = null;
        try {
            claims = jwt.decodeAccessToken(token);
        } catch (TokenExpiredException e) {
            throw new AuthorizationException(8003);
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            throw new AuthorizationException(8002);
        }
        return getClaim(claims);
    }

    @Override
    public boolean handleRefresh(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        String token = verifyHeader(request, response);
        Map<String, Claim> claims = null;
        try {
            claims = jwt.decodeRefreshToken(token);
        } catch (TokenExpiredException e) {
            throw new AuthorizationException(8004);
        } catch (AlgorithmMismatchException | SignatureVerificationException | JWTDecodeException | InvalidClaimException e) {
            throw new AuthorizationException(8005);
        }
        return getClaim(claims);
    }

    @Override
    public boolean handleUserPermission(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        handleLogin(request, response, meta);
        // 管理员自动拥有所有权限
        // 若是管理员则直接返回true
        if (handleAdmin(request, response, meta))
            return true;

        String permission = meta.permission();
        UserDO user = LocalUser.getLocalUser();
        // 得到当前权限的id
        QueryWrapper<PermissionDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(PermissionDO::getName, permission);
        PermissionDO permissionDO = permissionService.getOne(wrapper);
        //在user_permission表中查询 用户是否有该权限
        QueryWrapper<UserPermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserPermissionDO::getUserId, user.getId())
                .eq(UserPermissionDO::getPermissionId, permissionDO.getId());
        UserPermissionDO one = userPermissionService.getOne(queryWrapper);

        if (one == null) {
            throw new AuthorizationException(9997);
        }
        return true;
    }

    @Override
    public boolean handleAdmin(HttpServletRequest request, HttpServletResponse response, RouteMeta meta) {
        handleLogin(request, response, meta);
        UserDO user = LocalUser.getLocalUser();
        return user.getId() == 1;
    }

    @Override
    public boolean handleNotHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }


    private boolean getClaim(Map<String, Claim> claims) {
        if (claims == null) {
            throw new AuthorizationException(8002);
        }
        int identity = claims.get("identity").asInt();
        UserDO user = userService.getById(identity);
        if (user == null) {
            throw new NotFoundException(30001);
        }
        LocalUser.setLocalUser(user);
        return true;
    }


    private String verifyHeader(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader("Authorization");
        if (header == null || header.isEmpty()) {
            throw new AuthorizationException(8001);
        }
        if (!header.startsWith("Bearer")) {
            throw new AuthorizationException(8002);
        }
        String token = header.replace("Bearer ", "");
        return token;
    }
}
