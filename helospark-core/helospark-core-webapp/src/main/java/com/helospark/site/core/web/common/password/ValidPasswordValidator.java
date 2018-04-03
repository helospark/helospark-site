package com.helospark.site.core.web.common.password;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.RuleResult;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(6, 50)));

        RuleResult result = validator.validate(new PasswordData(new Password(password)));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(composeMessage(validator, result))
                .addConstraintViolation();
        return false;
    }

    private String composeMessage(PasswordValidator validator, RuleResult result) {
        return validator.getMessages(result)
                .stream()
                .collect(Collectors.joining(","));
    }

}
