/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/15 15:30
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
@TableName("question")
public class QuestionDO extends BaseModel implements Serializable {
    private static final long serialVersionUID = -7870420465016671851L;
    private String title;
    private String summary;
    private String image;
    private String content;
}
