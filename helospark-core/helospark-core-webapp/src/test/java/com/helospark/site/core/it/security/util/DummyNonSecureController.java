package com.helospark.site.core.it.security.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyNonSecureController {

    @GetMapping("/not_secure")
    public String nonSecure() {
        return "Not secure";
    }

}
