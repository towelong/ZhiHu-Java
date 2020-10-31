/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/26 13:45
 */
package com.towelong.zhihu.dto.reply;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
public class CreateReplyDTO {
//    private Integer userId;
    @Positive(message = "{id.positive}")
    private Integer toUserId;
    @Positive(message = "{id.positive}")
    private Integer questionId;
    @Positive(message = "{id.positive}")
    private Integer commentId;
    @NotEmpty(message = "reply.content")
    private String content;
}
