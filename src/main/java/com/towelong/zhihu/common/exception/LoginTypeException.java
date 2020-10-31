/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/22 13:34
 */
package com.towelong.zhihu.common.exception;

public class LoginTypeException extends HttpException {
    public LoginTypeException(int code) {
        this.code = code;
        this.httpStatusCode = 403;
    }
}
