/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 10:53
 */
package com.towelong.zhihu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("reply")
public class ReplyDO extends BaseModel implements Serializable {
    private static final long serialVersionUID = -5335203905297978552L;
    private Integer userId;
    private Integer toUserId;
    private Integer questionId;
    private Integer commentId;
    private String content;
}
