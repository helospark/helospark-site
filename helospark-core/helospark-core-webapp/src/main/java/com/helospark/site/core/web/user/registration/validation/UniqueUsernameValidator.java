package com.helospark.site.core.web.user.registration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private ApplicationUserRepository repository;

    public UniqueUsernameValidator(ApplicationUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || repository.findByUsername(value) == null;
    }

}
