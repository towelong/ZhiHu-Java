/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 17:56
 */
package com.towelong.zhihu.vo;

import com.towelong.zhihu.model.QuestionDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailVo {
    private QuestionDO question;
    private CommentVo comment;
    private ReplyVo reply;
}
