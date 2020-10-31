/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 17:57
 */
package com.towelong.zhihu.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ResultResponse<T> {

    private int code;
    private T data;
    private String message;

    public ResultResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = "ok";
    }

    public ResultResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
