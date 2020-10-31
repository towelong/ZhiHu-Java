/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 18:52
 */
package com.towelong.zhihu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.towelong.zhihu.dto.user.ChangePasswordDTO;
import com.towelong.zhihu.dto.user.CreateOrUpdateUserDTO;
import com.towelong.zhihu.dto.user.UserLoginDTO;
import com.towelong.zhihu.model.PermissionDO;
import com.towelong.zhihu.model.UserDO;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<UserDO> {
    /*
     * 客户端API
     */
    boolean createUser(CreateOrUpdateUserDTO validate);

    UserDO selectUserByAccount(String account);
    UserDO selectUserById(Integer id);

    UserDO userLogin(UserLoginDTO validate);

    boolean updateUserPassword(ChangePasswordDTO validate);


}
