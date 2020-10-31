/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/20 18:25
 */
package com.towelong.zhihu.common.validator.impl;

import com.towelong.zhihu.common.validator.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

public class EnumValidator implements ConstraintValidator<Enum,Object> {

    private Class<?> cls; //枚举类

    private boolean allowNull;

    @Override
    public void initialize(Enum constraintAnnotation) {
        cls = constraintAnnotation.target();
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null && allowNull){
            return true;
        }
        if (cls.isEnum()){
            Object[] objects = cls.getEnumConstants();
            try {
                Method method = cls.getMethod("getTypeCode");
                for (Object obj: objects){
                    Object vals = method.invoke(obj);
                    if(vals.equals(value)){
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
