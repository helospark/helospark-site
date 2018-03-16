package com.helospark.site.core.web.security.annotation;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = FORBIDDEN)
public class UserNotLoggedInException extends RuntimeException {

    public UserNotLoggedInException(String string) {
        super(string);
    }

}
