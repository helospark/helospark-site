package com.helospark.site.core.service.article.comment;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND)
public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(String arg0) {
        super(arg0);
    }

}
