/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 11:40
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.common.exception.ExistException;
import com.towelong.zhihu.dto.comment.CreateOrUpdateCommentDTO;
import com.towelong.zhihu.mapper.CommentMapper;
import com.towelong.zhihu.model.CommentDO;
import com.towelong.zhihu.model.QuestionDO;
import com.towelong.zhihu.service.CommentService;
import com.towelong.zhihu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDO> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionService questionService;

    @Override
    public IPage<CommentDO> selectAll(int page, int size) {
        Page<CommentDO> pages = new Page<>(page, size);
        QueryWrapper<CommentDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CommentDO::getIsParent, true).orderByDesc(CommentDO::getCreateTime);
        return this.baseMapper.selectPage(pages, wrapper);
    }

    @Override
    public IPage<CommentDO> selectCommentByQuestionId(int page, int size, Integer questionId, Boolean isParent) {
        Page<CommentDO> pages = new Page<>(page, size);
        QueryWrapper<CommentDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CommentDO::getQuestionId, questionId).eq(CommentDO::getIsParent, isParent).orderByDesc(CommentDO::getCreateTime);
        return this.baseMapper.selectPage(pages, wrapper);
    }

    @Override
    public boolean createComment(CreateOrUpdateCommentDTO validate) {
        QuestionDO question = questionService.selectQuestionDetail(validate.getQuestionId());
        if (question == null) {
            throw new ExistException(20000);
        }
        CommentDO comment = new CommentDO();
        comment.setUserId(LocalUser.getLocalUser().getId());
        comment.setContent(validate.getContent());
        comment.setQuestionId(validate.getQuestionId());
        comment.setIsParent(validate.getIsParent());
        return commentMapper.insert(comment) > 0;
    }

    @Override
    public CommentDO selectCommentByCommentIdAndQuestionId(Integer commentId, Integer questionId) {
        QueryWrapper<CommentDO> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(CommentDO::getQuestionId, questionId)
                .eq(CommentDO::getId, commentId);
        return this.baseMapper.selectOne(wrapper);
    }

}
