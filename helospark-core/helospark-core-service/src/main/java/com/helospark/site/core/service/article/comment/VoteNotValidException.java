package com.helospark.site.core.service.article.comment;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = BAD_REQUEST)
public class VoteNotValidException extends RuntimeException {

    public VoteNotValidException(String arg0) {
        super(arg0);
    }

}
