/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/18 13:32
 */
package com.towelong.zhihu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.towelong.zhihu.dto.question.CreateQuestionDTO;
import com.towelong.zhihu.model.QuestionDO;
import com.towelong.zhihu.vo.QuestionVo;

public interface QuestionService extends IService<QuestionDO> {
    /**
     * 查询问题id为$id 的内容
     * @param id 问题id
     * @return QuestionDO
     */
    QuestionDO selectQuestionDetail(Integer id);

    /**
     * 查找所有问题
     * @param page 当前页
     * @param size 每页的大小
     * @return 问题分页列表
     */
    IPage<QuestionVo> selectPageQuestion(int page,int size);

    /**
     * 创建问题
     * @return 是否成功
     */
    boolean createQuestion(CreateQuestionDTO validate);


    boolean updateQuestion(QuestionDO question,CreateQuestionDTO validate);


}
