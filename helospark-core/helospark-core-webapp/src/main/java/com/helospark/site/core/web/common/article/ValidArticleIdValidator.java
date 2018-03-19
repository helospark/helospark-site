package com.helospark.site.core.web.common.article;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.helospark.site.core.service.article.domain.repository.ArticleRepository;

public class ValidArticleIdValidator implements ConstraintValidator<ValidArticleId, Integer> {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || articleRepository.existsById(value);
    }
}
