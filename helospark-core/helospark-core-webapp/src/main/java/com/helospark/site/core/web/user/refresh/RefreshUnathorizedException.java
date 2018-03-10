package com.helospark.site.core.web.user.refresh;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = UNAUTHORIZED)
public class RefreshUnathorizedException extends RuntimeException {

    public RefreshUnathorizedException(String string) {
        super(string);
    }

}
