package com.gentleman.server.annotation.valitionSex;

import com.gentleman.server.annotation.SexAnnotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 一粒尘埃
 * @date 2020/11/3/15:56
 */
public class SexValiator implements ConstraintValidator<SexAnnotation,Integer> {

    Set<Integer> sexs;

    @Override
    public void initialize(SexAnnotation constraintAnnotation) {
        sexs = new HashSet<Integer>();
        sexs.add(1);
        sexs.add(2);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(sexs.contains(value)){
            return true;
        }
        return false;
    }
}
