/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/14 22:24
 */
package com.towelong.zhihu.common.exception;

public class NotFoundException extends HttpException{

    public NotFoundException(int code) {
        this.code = code;
        this.httpStatusCode = 404;
    }
}
