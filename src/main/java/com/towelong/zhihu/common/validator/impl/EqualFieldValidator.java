/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/28 17:47
 */
package com.towelong.zhihu.common.validator.impl;

import com.towelong.zhihu.common.validator.EqualField;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class EqualFieldValidator implements ConstraintValidator<EqualField, Object> {

    private String srcField;
    private String dstField;

    @Override
    public void initialize(EqualField constraintAnnotation) {
        this.srcField = constraintAnnotation.srcField();
        this.dstField = constraintAnnotation.dstField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Class<?> clazz = object.getClass();
        Field srcField = ReflectionUtils.findField(clazz, this.srcField);
        Field dstField = ReflectionUtils.findField(clazz, this.dstField);
        try {
            if (srcField == null || dstField == null)
                return false;
            srcField.setAccessible(true);
            dstField.setAccessible(true);
            String src = (String) srcField.get(object);
            String dst = (String) dstField.get(object);
            if (src.equals(dst))
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
