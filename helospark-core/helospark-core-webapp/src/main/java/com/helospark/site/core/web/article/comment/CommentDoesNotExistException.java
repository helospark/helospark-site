package com.helospark.site.core.web.article.comment;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class CommentDoesNotExistException extends RuntimeException {

    public CommentDoesNotExistException(String arg0) {
        super(arg0);
    }

}
