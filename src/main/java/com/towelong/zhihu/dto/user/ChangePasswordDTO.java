/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/28 17:54
 */
package com.towelong.zhihu.dto.user;


import com.towelong.zhihu.common.validator.EqualField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@EqualField(srcField = "newPassword", dstField = "confirmPassword",message = "{password.equal}")
public class ChangePasswordDTO {

    @NotBlank(message = "{user.password}")
    private String oldPassword;

    @NotBlank(message = "{password.not-blank}")
    private String newPassword;

    @NotBlank(message = "{password.not-blank}")
    private String confirmPassword;
}
