/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 13:59
 */
package com.towelong.zhihu.vo;

import com.towelong.zhihu.model.CommentDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CommentVo {
    private List<CommentDO> commentList;
    private Integer page;
    private Integer size;
    private Long total;
}
