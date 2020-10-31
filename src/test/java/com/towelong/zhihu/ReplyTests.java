/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 11:49
 */
package com.towelong.zhihu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.towelong.zhihu.model.ReplyDO;
import com.towelong.zhihu.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReplyTests {
    @Autowired
    private ReplyService replyService;

    @Test
    void ReplyPage() {
        IPage<ReplyDO> replyDOIPage = replyService.selectReplyByQuestionId(1,10,1);
        System.out.println(replyDOIPage);
    }
}
