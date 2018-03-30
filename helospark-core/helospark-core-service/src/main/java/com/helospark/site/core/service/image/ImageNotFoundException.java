package com.helospark.site.core.service.image;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String string) {
        super(string);
    }

}
