package com.helospark.site.core.web.common.comment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.helospark.site.core.service.article.comment.repository.CommentRepository;

public class ValidCommentIdValidator implements ConstraintValidator<ValidCommentId, Integer> {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || commentRepository.existsById(value);
    }
}
