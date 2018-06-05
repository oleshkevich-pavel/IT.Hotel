package com.itacademy.jd2.po.hotel.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TodayOrFutureValidator.class)
@Documented
public @interface TodayOrFuture {
    String message() default "must be today or in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
