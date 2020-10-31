/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/15 15:49
 */
package com.towelong.zhihu.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.towelong.zhihu.common.ResultResponse;
import com.towelong.zhihu.common.UnifyResponse;
import com.towelong.zhihu.common.annotation.LoginRequired;
import com.towelong.zhihu.common.configuration.CodeMessageConfiguration;
import com.towelong.zhihu.common.exception.NotFoundException;
import com.towelong.zhihu.dto.question.CreateQuestionDTO;
import com.towelong.zhihu.model.CommentDO;
import com.towelong.zhihu.model.QuestionDO;
import com.towelong.zhihu.model.ReplyDO;
import com.towelong.zhihu.service.CommentService;
import com.towelong.zhihu.service.QuestionService;
import com.towelong.zhihu.service.ReplyService;
import com.towelong.zhihu.vo.CommentVo;
import com.towelong.zhihu.vo.QuestionDetailVo;
import com.towelong.zhihu.vo.QuestionVo;
import com.towelong.zhihu.vo.ReplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;

    @Autowired
    private CodeMessageConfiguration code;

    @GetMapping("/{id}")
    public ResultResponse<QuestionDetailVo> getQuestion
            (@PathVariable(value = "id") @Positive(message = "{id.positive}") Integer id,
             @RequestParam(defaultValue = "1") Integer page,
             @RequestParam(defaultValue = "10") Integer count) {
        IPage<CommentDO> commentDOIPage = commentService.selectCommentByQuestionId(page, count, id, true);
        IPage<ReplyDO> replyDOIPage = replyService.selectReplyByQuestionId(page, count, id);
        QuestionDO questionDO = questionService.selectQuestionDetail(id);
        CommentVo commentVo = new CommentVo(commentDOIPage.getRecords(), page, count, commentDOIPage.getTotal());
        ReplyVo replyVo = new ReplyVo(replyDOIPage.getRecords(), page, count, replyDOIPage.getTotal());
        QuestionDetailVo vo = QuestionDetailVo.builder()
                .question(questionDO)
                .comment(commentVo)
                .reply(replyVo)
                .build();
        return new ResultResponse<>(0, vo);
    }

    @GetMapping("/all")
    public ResultResponse<QuestionVo> getPageQuestion(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer count) {
        QuestionVo questionVo = new QuestionVo();
        IPage<QuestionVo> questionVoIPage = questionService.selectPageQuestion(page, count);
        questionVo.setCount(count);
        questionVo.setPage(page);
        questionVo.setTotal(questionVoIPage.getTotal());
        List<QuestionVo> records = questionVoIPage.getRecords();
        questionVo.setQuestion(records);
        return new ResultResponse<>(0, questionVo);
    }

    @PostMapping("")
    @LoginRequired
    public UnifyResponse insertQuestion(@Validated @RequestBody CreateQuestionDTO validate) {
        questionService.createQuestion(validate);
        return new UnifyResponse(30, code.getMessage(30));
    }

    @PutMapping("/{id}")
    @LoginRequired
    public UnifyResponse updateQuestion(
            @PathVariable("id") @Positive(message = "{id.positive}") Long id,
            @Validated @RequestBody CreateQuestionDTO validate) {
        QuestionDO questionDO = questionService.getById(id);
        if (questionDO == null) {
            throw new NotFoundException(20000);
        }
        questionService.updateQuestion(questionDO, validate);
        return new UnifyResponse(31, code.getMessage(31));
    }


}
