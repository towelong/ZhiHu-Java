/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/24 13:08
 */
package com.towelong.zhihu.common.exception;

public class AuthorizationException extends HttpException {

    public AuthorizationException(int code) {
        this.code = code;
        this.httpStatusCode = 401;
    }
}
