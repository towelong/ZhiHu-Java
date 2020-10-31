package com.towelong.zhihu.common.annotation;


import com.towelong.zhihu.common.enumeration.UserLevel;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Required(level = UserLevel.USER_PERMISSION)
public @interface UserPermissionRequired {
}
