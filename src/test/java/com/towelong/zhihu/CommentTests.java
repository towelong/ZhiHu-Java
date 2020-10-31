/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/19 11:49
 */
package com.towelong.zhihu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.towelong.zhihu.model.CommentDO;
import com.towelong.zhihu.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentTests {
    @Autowired
    private CommentService commentService;

    @Test
    void CommentPage() {
        IPage<CommentDO> commentDOIPage1 = commentService.selectAll(1,10);
        System.out.println(commentDOIPage1);
    }
}
