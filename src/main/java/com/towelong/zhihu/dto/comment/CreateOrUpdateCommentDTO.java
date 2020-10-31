/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 15:17
 */
package com.towelong.zhihu.dto.comment;

import com.towelong.zhihu.common.validator.Enum;
import com.towelong.zhihu.enumeration.BooleanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class CreateOrUpdateCommentDTO {
    @Positive(message = "{id.positive}")
    private Integer questionId;

    @NotEmpty(message = "{comment.not-empty}")
    private String content;

    @Enum(target = BooleanEnum.class)
    private Integer isParent;
}
