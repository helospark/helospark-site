package com.helospark.site.core.web.common.password;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = { ValidPasswordValidator.class })
public @interface ValidPassword {

    String message() default "Password does not follow complexity rules";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
