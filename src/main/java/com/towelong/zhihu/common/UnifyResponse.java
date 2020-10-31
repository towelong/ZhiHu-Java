/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/14 22:19
 */
package com.towelong.zhihu.common;

import com.towelong.zhihu.common.utils.RequestHelper;

public class UnifyResponse {

    private int code;
    private String message;
    private String request = RequestHelper.getRequestUrl();

    public UnifyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public UnifyResponse(String message) {
        this.code = 0;
        this.message = message;
    }

    public UnifyResponse(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getRequest() {
        return request;
    }
}
