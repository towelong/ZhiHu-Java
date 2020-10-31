/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/17 22:14
 */
package com.towelong.zhihu.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVo {
    private Integer page;
    private Integer count;
    private Long total;
    private List<QuestionVo> question;
}
