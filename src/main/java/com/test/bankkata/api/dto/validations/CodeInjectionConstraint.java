package com.test.bankkata.api.dto.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CodeInjectionValidator.class)
public @interface CodeInjectionConstraint {
    String message() default "Code injection detected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
