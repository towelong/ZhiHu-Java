/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 13:32
 */
package com.towelong.zhihu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.towelong.zhihu.dto.reply.CreateReplyDTO;
import com.towelong.zhihu.model.ReplyDO;

public interface ReplyService extends IService<ReplyDO> {
    /*
        每个reply 也是一个抽象的 comment
        通过questionId和commentId确定 所属问题下的评论和回复
        user_id为回复人id; to_user_id 为被回复人id
     */

    boolean createReply(CreateReplyDTO validate);


    /**
     * 通过 $commentId 查询 `评论` 对应的 `回应`（reply）
     * @param page 当前页
     * @param size 每页的大小
     * @param commentId 评论id
     * @return 分页的评论列表
     */
    IPage<ReplyDO> selectReplyByCommentId(int page,int size, Integer commentId);


    /**
     * 通过 $questionId 查询 问题下所有回复
     * @param page 当前页
     * @param size 每页的大小
     * @return 所有回复
     */
    IPage<ReplyDO> selectReplyByQuestionId(int page,int size,Integer questionId);


}
