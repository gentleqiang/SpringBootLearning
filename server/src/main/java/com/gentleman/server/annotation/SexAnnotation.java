package com.gentleman.server.annotation;

import com.gentleman.server.annotation.valitionSex.SexValiator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 一粒尘埃
 * @date 2020/11/3/15:53
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {SexValiator.class})
public @interface SexAnnotation {

    String message() default "性别只能是1:男 2:女";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
