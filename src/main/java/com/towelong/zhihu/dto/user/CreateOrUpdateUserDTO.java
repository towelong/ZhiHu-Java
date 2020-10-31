/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 18:11
 */
package com.towelong.zhihu.dto.user;

import com.towelong.zhihu.common.enumeration.LoginType;
import com.towelong.zhihu.common.validator.Enum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CreateOrUpdateUserDTO {
    @NotEmpty(message = "{user.nickname}")
    private String nickname;
    @NotEmpty(message = "{user.account}")
    private String account;
    @NotEmpty(message = "{user.password}")
    private String password;

    private String avatar;

    @Enum(target = LoginType.class)
    private Integer loginType;
}
