/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/13 17:40
 */
package com.towelong.zhihu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = -22139920072245591L;

    private String nickname;

    private String account;

    private String password;

    private String avatar;

    private Integer loginType;

}
