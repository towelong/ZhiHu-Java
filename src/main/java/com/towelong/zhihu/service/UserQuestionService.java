/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/11/7 13:07
 */
package com.towelong.zhihu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.towelong.zhihu.model.UserQuestionDO;
import com.towelong.zhihu.vo.UserSimpleVo;

import java.util.List;

public interface UserQuestionService extends IService<UserQuestionDO> {
    // 通过问题id查询用户简要信息
    UserSimpleVo selectUserByQuestionId(Integer questionId);

    // 通过用户id查询所有问题的id
    List<Integer> selectQuestionByUserId(Integer userId);
}
