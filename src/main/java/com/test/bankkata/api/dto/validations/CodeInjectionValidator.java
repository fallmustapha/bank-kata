package com.test.bankkata.api.dto.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeInjectionValidator implements ConstraintValidator<CodeInjectionConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value==null)
            return true;
        String linkPatternString = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String scriptPatternString = "(<scripts>(<script[^>]*/\\s*>)|(<script[^>]*\\s*>(([^<]*<\\s*\\!\\s*\\[\\s*cdata[^<]*)|([^<]*<\\s*\\!\\s*-\\s*-[^<]*)|([^<]*))<\\s*/\\s*script\\s*>))";
        Pattern linkPattern = Pattern.compile(linkPatternString);
        Matcher linkMatcher = linkPattern.matcher(value);
        Pattern scriptPattern = Pattern.compile(scriptPatternString);
        Matcher scriptMatcher = scriptPattern.matcher(value);

        return !linkMatcher.matches() && !scriptMatcher.matches();
    }
}
