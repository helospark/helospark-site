package com.helospark.site.core.web.user.refresh;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.web.security.domain.AuthenticationResult;

@RestController
public class TokenRefreshController {
    private TokenRefreshService tokenRefreshService;

    public TokenRefreshController(TokenRefreshService tokenRefreshService) {
        this.tokenRefreshService = tokenRefreshService;
    }

    @PostMapping("/users/login/refresh")
    public AuthenticationResult refreshToken(@RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return tokenRefreshService.refreshToken(tokenRefreshRequest.getRefreshToken());
    }

    @ExceptionHandler(RefreshUnathorizedException.class)
    public ResponseEntity<String> exceptionHandler(RefreshUnathorizedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

}
