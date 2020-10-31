/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/15 15:33
 */
package com.towelong.zhihu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.towelong.zhihu.model.QuestionDO;
import com.towelong.zhihu.vo.QuestionVo;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionMapper extends BaseMapper<QuestionDO> {
    /**
     * 查询问题id为$id 的内容
     * @param id 问题id
     * @return QuestionDO
     */
    QuestionDO selectQuestionDetail(Integer id);

    /**
     * 查找所有问题
     * @param page 分页对象
     * @return 问题分页列表
     */
    IPage<QuestionVo> selectPageQuestion(Page<QuestionDO> page);
}
