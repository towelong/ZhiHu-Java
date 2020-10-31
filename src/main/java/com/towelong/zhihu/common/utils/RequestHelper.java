/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/14 22:20
 */
package com.towelong.zhihu.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static String getRequestUrl() {
        String methods = getRequest().getMethod();
        return methods + " " + getRequest().getServletPath();
    }

    public static long getUid() {
        return (long)RequestHelper.getRequest().getAttribute("uid");
    }

}
