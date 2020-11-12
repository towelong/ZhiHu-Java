/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 13:33
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.dto.question.CreateQuestionDTO;
import com.towelong.zhihu.mapper.QuestionMapper;
import com.towelong.zhihu.mapper.UserQuestionMapper;
import com.towelong.zhihu.model.QuestionDO;
import com.towelong.zhihu.model.UserQuestionDO;
import com.towelong.zhihu.service.QuestionService;
import com.towelong.zhihu.service.UserQuestionService;
import com.towelong.zhihu.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionDO> implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserQuestionMapper userQuestionMapper;
    @Autowired
    private UserQuestionService userQuestionService;

    @Override
    public IPage<QuestionVo> selectUserQuestion(Integer userId, int page, int size) {
        List<Integer> questionIds = userQuestionService.selectQuestionByUserId(userId);
        Page<QuestionDO> pages = new Page<>(page, size);
        return questionMapper.selectPageQuestionByUser(questionIds,pages);
    }

    @Override
    public QuestionDO selectQuestionDetail(Integer id) {
        return questionMapper.selectQuestionDetail(id);
    }

    @Override
    public IPage<QuestionVo> selectPageQuestion(int page, int size) {
        Page<QuestionDO> pages = new Page<>(page, size);
        return questionMapper.selectPageQuestion(pages);
    }

    @Override
    public boolean createQuestion(CreateQuestionDTO validate) {
        QuestionDO questionDO = QuestionDO.builder()
                .title(validate.getTitle())
                .content(validate.getContent())
                .image(validate.getImage())
                .summary(validate.getContent())
                .build();
        questionMapper.insert(questionDO);
        UserQuestionDO userQuestionDO = UserQuestionDO.builder()
                .questionId(questionDO.getId())
                .userId(LocalUser.getLocalUser().getId())
                .build();
        return userQuestionMapper.insert(userQuestionDO) > 0;
    }

    @Override
    public boolean updateQuestion(QuestionDO question, CreateQuestionDTO validate) {
        question.setTitle(validate.getTitle());
        question.setImage(validate.getImage());
        question.setContent(validate.getContent());
        question.setSummary(validate.getContent());
        return questionMapper.updateById(question) > 0;
    }


}
