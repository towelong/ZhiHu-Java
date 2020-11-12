/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/24 13:30
 */
package com.towelong.zhihu.common.interceptor;

import com.towelong.zhihu.common.annotation.Required;
import com.towelong.zhihu.common.annotation.RouteMeta;
import com.towelong.zhihu.common.enumeration.UserLevel;
import com.towelong.zhihu.common.interfaces.AuthorizeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
public class AuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthorizeHandler authorizeHandler;

    private String[] excludeMethods = new String[]{"OPTIONS"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkInExclude(request.getMethod())) {
            // 有些请求方法无需检测，如OPTIONS
            return true;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RouteMeta meta = method.getAnnotation(RouteMeta.class);
            // 考虑两种情况，1. 有 meta；2. 无 meta
            if (meta == null) {
                // 无meta的话，第一种情况，可能有adminRequired和loginRequired
                // 第二种情况，可能没有权限注解
                return this.handleNoMeta(request, response, method);
            } else {
                // 有meta在权限范围之内，需要判断权限
                return this.handleMeta(request, response, method, meta);
            }
        } else {
            // handler不是HandlerMethod的情况
            return authorizeHandler.handleNotHandlerMethod(request, response, handler);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private boolean handleNoMeta(HttpServletRequest request, HttpServletResponse response, Method method) {
        Annotation[] annotations = method.getAnnotations();
        UserLevel level = findRequired(annotations);
        switch (level) {
            case LOGIN:
                // 登陆权限
                return authorizeHandler.handleLogin(request, response, null);
            case ADMIN:
                // 管理员权限
                return authorizeHandler.handleAdmin(request, response, null);
            case REFRESH:
                // 刷新令牌
                return authorizeHandler.handleRefresh(request, response, null);
            case USER_PERMISSION:
                // 单个权限
                return authorizeHandler.handleUserPermission(request, response, null);
            default:
                return true;
        }
    }

    private boolean handleMeta(HttpServletRequest request, HttpServletResponse response, Method method, RouteMeta meta) {
        if (!meta.mount()) {
            return this.handleNoMeta(request, response, method);
        }
        Annotation[] annotations = method.getAnnotations();
        UserLevel level = findRequired(annotations);
        switch (level) {
            case LOGIN:
                // 登陆权限
                return authorizeHandler.handleLogin(request, response, meta);
            case USER_PERMISSION:
                // 单个权限
                return authorizeHandler.handleUserPermission(request, response, meta);
            case ADMIN:
                // 管理员权限
                return authorizeHandler.handleAdmin(request, response, meta);
            case REFRESH:
                // 刷新令牌
                return authorizeHandler.handleRefresh(request, response, meta);
            default:
                return true;
        }
    }


    public static UserLevel findRequired(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> aClass = annotation.annotationType();
            Required required = aClass.getAnnotation(Required.class);
            if (required != null) {
                return required.level();
            }
        }
        return UserLevel.TOURIST;
    }

    private boolean checkInExclude(String method) {
        for (String excludeMethod : excludeMethods) {
            if (method.equals(excludeMethod)) {
                return true;
            }
        }
        return false;
    }
}
