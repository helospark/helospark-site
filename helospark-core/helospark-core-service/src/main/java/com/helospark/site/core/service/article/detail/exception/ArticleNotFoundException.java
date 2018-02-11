package com.helospark.site.core.service.article.detail.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(String message) {
        super(message);
    }

}
