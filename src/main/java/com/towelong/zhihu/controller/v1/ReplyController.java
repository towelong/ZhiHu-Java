/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/26 13:42
 */
package com.towelong.zhihu.controller.v1;


import com.towelong.zhihu.common.UnifyResponse;
import com.towelong.zhihu.common.annotation.LoginRequired;
import com.towelong.zhihu.common.configuration.CodeMessageConfiguration;
import com.towelong.zhihu.dto.reply.CreateReplyDTO;
import com.towelong.zhihu.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/reply")
public class ReplyController {

    @Autowired
    private CodeMessageConfiguration code;

    @Autowired
    private ReplyService replyService;

    @PostMapping("")
    @LoginRequired
    public UnifyResponse insertReply(@RequestBody @Validated CreateReplyDTO validate){
        replyService.createReply(validate);
        return new UnifyResponse(40,code.getMessage(40));
    }
}
