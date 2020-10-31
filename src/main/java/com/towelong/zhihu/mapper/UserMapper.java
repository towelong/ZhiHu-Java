/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/13 17:45
 */
package com.towelong.zhihu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.towelong.zhihu.model.UserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserDO> {

}
