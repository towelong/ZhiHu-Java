/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/26 12:30
 */
package com.towelong.zhihu.dto.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQuestionDTO {

    @NotEmpty(message = "{question.title}")
    private String title;
    private String summary;
    private String image;
    @NotEmpty(message = "{question.content}")
    private String content;
}
