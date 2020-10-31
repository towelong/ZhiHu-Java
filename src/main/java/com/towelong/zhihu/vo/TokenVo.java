/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/29 12:19
 */
package com.towelong.zhihu.vo;

import lombok.Data;

@Data
public class TokenVo {
    private String accessToken;
    private String refreshToken;
}
