package com.helospark.site.core.it.security.util;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummySecureController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/secret")
    public String secret() {
        return "Secret";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/secret_with_user_role")
    public String secretWithRole() {
        return "Secret with role";
    }
}
