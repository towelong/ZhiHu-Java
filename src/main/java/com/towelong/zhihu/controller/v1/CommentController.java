/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 15:01
 */
package com.towelong.zhihu.controller.v1;

import com.towelong.zhihu.common.UnifyResponse;
import com.towelong.zhihu.common.annotation.LoginRequired;
import com.towelong.zhihu.common.configuration.CodeMessageConfiguration;
import com.towelong.zhihu.dto.comment.CreateOrUpdateCommentDTO;
import com.towelong.zhihu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CodeMessageConfiguration code;

    @PostMapping("")
    @LoginRequired
    public UnifyResponse createComment(@RequestBody @Validated CreateOrUpdateCommentDTO validate){
        commentService.createComment(validate);
        return new UnifyResponse(10,code.getMessage(10));
    }

}
