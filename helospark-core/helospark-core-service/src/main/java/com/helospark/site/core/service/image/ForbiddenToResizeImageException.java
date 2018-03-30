package com.helospark.site.core.service.image;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = FORBIDDEN)
public class ForbiddenToResizeImageException extends RuntimeException {

}
