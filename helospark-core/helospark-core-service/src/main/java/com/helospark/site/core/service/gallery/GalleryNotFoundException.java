package com.helospark.site.core.service.gallery;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = NOT_FOUND)
public class GalleryNotFoundException extends RuntimeException {

    public GalleryNotFoundException(String string) {
        super(string);
    }

}
