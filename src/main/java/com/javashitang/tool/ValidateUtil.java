package com.javashitang.tool;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidateUtil {

    /**
     * 属性校验的工具类
     */
    public static String validate(Object o, Class<?>... groups) {
        ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactor.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(o, groups);
        if (set == null || set.isEmpty()) {
            return null;
        }
        for (ConstraintViolation<Object> constraintViolation : set) {
            return constraintViolation.getMessage();
        }
        return null;
    }
}
