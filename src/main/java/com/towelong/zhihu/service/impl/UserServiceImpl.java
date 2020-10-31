/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 18:55
 */
package com.towelong.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.towelong.zhihu.common.LocalUser;
import com.towelong.zhihu.common.enumeration.LoginType;
import com.towelong.zhihu.common.exception.ExistException;
import com.towelong.zhihu.common.exception.LoginTypeException;
import com.towelong.zhihu.common.utils.EncyptUtils;
import com.towelong.zhihu.dto.user.ChangePasswordDTO;
import com.towelong.zhihu.dto.user.CreateOrUpdateUserDTO;
import com.towelong.zhihu.dto.user.UserLoginDTO;
import com.towelong.zhihu.mapper.UserMapper;
import com.towelong.zhihu.model.UserDO;
import com.towelong.zhihu.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public boolean createUser(CreateOrUpdateUserDTO validate) {
        UserDO user = selectUserByAccount(validate.getAccount());
        if (user != null) {
            throw new ExistException(30000);
        }
        UserDO userDO = new UserDO();
        userDO.setAccount(validate.getAccount());
        userDO.setLoginType(validate.getLoginType());
        userDO.setNickname(validate.getNickname());
        userDO.setPassword(EncyptUtils.setPasswordEncrypt(validate.getPassword()));
        return this.baseMapper.insert(userDO) > 0;
    }

    @Override
    public UserDO selectUserByAccount(String account) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getAccount, account);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public UserDO selectUserById(Integer id) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getId, id);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public UserDO userLogin(UserLoginDTO validate) {
        LoginType loginType = LoginType.getTypeByValue(validate.getLoginType());
        if (loginType == null) {
            throw new LoginTypeException(9998);
        }
        switch (loginType) {
            case MINI:
                break;
            case USER_PASSWORD:
                UserDO userDO = UserPasswordLogin(validate);
                if (EncyptUtils.encrypt(validate.getPassword(), userDO.getPassword())) {
                    return userDO;
                }
                throw new ExistException(30002);
            default:
                return null;
        }
        return null;
    }

    @Override
    public boolean updateUserPassword(ChangePasswordDTO validate) {
        UserDO user = LocalUser.getLocalUser();
        UserDO userDO = this.getById(user.getId());
        boolean isTrue = EncyptUtils.encrypt(validate.getOldPassword(), user.getPassword());
        if (isTrue) {
            String passwordEncrypt = EncyptUtils.setPasswordEncrypt(validate.getNewPassword());
            userDO.setPassword(passwordEncrypt);
            return this.baseMapper.updateById(userDO) > 0;
        }
        throw new ExistException(30003);
    }


    private UserDO UserPasswordLogin(UserLoginDTO validate) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getAccount, validate.getAccount());
        UserDO userDO = this.baseMapper.selectOne(wrapper);
        if (userDO == null) {
            throw new ExistException(30001);
        }
        return userDO;
    }
}
