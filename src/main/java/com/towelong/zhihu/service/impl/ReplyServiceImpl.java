/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 12:54
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.common.exception.NotFoundException;
import com.towelong.zhihu.dto.comment.CreateOrUpdateCommentDTO;
import com.towelong.zhihu.dto.reply.CreateReplyDTO;
import com.towelong.zhihu.mapper.ReplyMapper;
import com.towelong.zhihu.model.CommentDO;
import com.towelong.zhihu.model.ReplyDO;
import com.towelong.zhihu.model.UserDO;
import com.towelong.zhihu.service.CommentService;
import com.towelong.zhihu.service.ReplyService;
import com.towelong.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, ReplyDO> implements ReplyService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public boolean createReply(CreateReplyDTO validate) {
        Integer toUserId = validate.getToUserId();
        String content = validate.getContent();
        Integer questionId = validate.getQuestionId();
        Integer commentId = validate.getCommentId();

        // 1. 确定该问题下的评论是否存在
        CommentDO commentDO = commentService.selectCommentByCommentIdAndQuestionId(commentId, questionId);
        if (commentDO == null)
            throw new NotFoundException(40000);

        // 2. 确定被回复人是否存在 且 被回复人在当前问题下是否评论过
        UserDO user = userService.selectUserById(toUserId);
        if (user == null)
            throw new NotFoundException(30001);
        if (!toUserId.equals(commentDO.getUserId()))
            throw new NotFoundException(40001);
        // 3. 创建reply
        ReplyDO replyDO = ReplyDO.builder()
                .userId(LocalUser.getLocalUser().getId())
                .toUserId(toUserId)
                .content(content)
                .questionId(questionId)
                .commentId(commentId)
                .build();
        // 4. 创建评论（回复是特殊的评论）
        CreateOrUpdateCommentDTO comment = new CreateOrUpdateCommentDTO();
        comment.setContent(content);
        comment.setQuestionId(questionId);
        comment.setIsParent(0);
        commentService.createComment(comment);
        return replyMapper.insert(replyDO) > 0;
    }

    @Override
    public IPage<ReplyDO> selectReplyByCommentId(int page, int size, Integer commentId) {
        Page<ReplyDO> pages = new Page<>(page, size);
        QueryWrapper<ReplyDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ReplyDO::getCommentId, commentId);
        return this.baseMapper.selectPage(pages, wrapper);

    }

    @Override
    public IPage<ReplyDO> selectReplyByQuestionId(int page, int size, Integer questionId) {
        Page<ReplyDO> pages = new Page<>(page, size);
        QueryWrapper<ReplyDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ReplyDO::getQuestionId, questionId);
        return this.baseMapper.selectPage(pages, wrapper);
    }

}
