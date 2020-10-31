/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 13:32
 */
package com.towelong.zhihu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.towelong.zhihu.dto.comment.CreateOrUpdateCommentDTO;
import com.towelong.zhihu.model.CommentDO;

public interface CommentService extends IService<CommentDO> {

    IPage<CommentDO> selectAll(int page, int size);

    /**
     * 通过 $questionId 查询问题对应的评论
     *
     * @param page       当前页
     * @param size       数量
     * @param questionId 问题id
     * @param isParent   是否为父节点
     * @return 分页的评论列表
     */
    IPage<CommentDO> selectCommentByQuestionId(int page, int size, Integer questionId, Boolean isParent);

    boolean createComment(CreateOrUpdateCommentDTO validate);

    // reply 通过comment_id和question_id 唯一确定
    CommentDO selectCommentByCommentIdAndQuestionId(Integer commentId,Integer questionId);
}
