package com.helospark.site.core.web.security;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = FORBIDDEN)
public class UnknownUserException extends RuntimeException {

    public UnknownUserException(String arg0) {
        super(arg0);
    }

}
