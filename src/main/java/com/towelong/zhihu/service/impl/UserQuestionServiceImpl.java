/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/11/7 13:09
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.mapper.UserQuestionMapper;
import com.towelong.zhihu.model.UserDO;
import com.towelong.zhihu.model.UserQuestionDO;
import com.towelong.zhihu.service.UserQuestionService;
import com.towelong.zhihu.service.UserService;
import com.towelong.zhihu.vo.UserSimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserQuestionServiceImpl extends ServiceImpl<UserQuestionMapper, UserQuestionDO> implements UserQuestionService {

    @Autowired
    private UserService userService;

    @Override
    public UserSimpleVo selectUserByQuestionId(Integer questionId) {
        QueryWrapper<UserQuestionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserQuestionDO::getQuestionId, questionId);
        UserQuestionDO userQuestionDO = this.baseMapper.selectOne(queryWrapper);
        UserDO userDO = userService.selectUserById(userQuestionDO.getUserId());
        return UserSimpleVo.builder().avatar(userDO.getAvatar()).nickname(userDO.getNickname()).build();
    }

    @Override
    public List<Integer> selectQuestionByUserId(Integer userId) {
        QueryWrapper<UserQuestionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserQuestionDO::getUserId, userId);
        List<UserQuestionDO> userQuestionList = this.baseMapper.selectList(queryWrapper);
        return userQuestionList.stream().map(UserQuestionDO::getQuestionId).collect(Collectors.toList());
    }
}
