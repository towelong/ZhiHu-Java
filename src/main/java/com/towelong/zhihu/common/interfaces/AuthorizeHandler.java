/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/24 12:54
 */
package com.towelong.zhihu.common.interfaces;

import com.towelong.zhihu.common.annotation.RouteMeta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthorizeHandler {

    boolean handleLogin(HttpServletRequest request, HttpServletResponse response, RouteMeta meta);
    boolean handleRefresh(HttpServletRequest request, HttpServletResponse response, RouteMeta meta);
    boolean handleUserPermission(HttpServletRequest request, HttpServletResponse response, RouteMeta meta);

    boolean handleAdmin(HttpServletRequest request, HttpServletResponse response, RouteMeta meta);

    boolean handleNotHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler);
}
