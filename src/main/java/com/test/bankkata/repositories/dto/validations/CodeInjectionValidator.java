package com.test.bankkata.repositories.dto.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodeInjectionValidator implements ConstraintValidator<CodeInjectionConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}
