/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/24 16:03
 */
package com.towelong.zhihu.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user_permission")
public class UserPermissionDO extends BaseModel implements Serializable {
    private static final long serialVersionUID = 6860584604434172662L;
    private Integer userId;
    private Integer permissionId;
}
