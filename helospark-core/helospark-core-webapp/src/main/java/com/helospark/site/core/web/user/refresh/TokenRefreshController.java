package com.helospark.site.core.web.user.refresh;

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

}
