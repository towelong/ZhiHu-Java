/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 13:28
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
@TableName("comment")
public class CommentDO extends BaseModel implements Serializable {
    private static final long serialVersionUID = -5962467376242381042L;
    private Integer questionId;
    private String content;
    private Integer userId;
    private Integer isParent;

}
