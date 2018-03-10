package com.helospark.site.core.web.user.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.web.security.domain.AuthenticationResult;
import com.helospark.site.core.web.security.helper.AuthenticationService;

@RestController
@RequestMapping("/users")
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AuthenticationResult attemptAuthentication(@RequestBody ApplicationUser applicationUser) {
        Authentication authentication = tryAuthenticate(applicationUser);
        User user = (User) authentication.getPrincipal();
        return authenticationService.successfulAuthentication(user);
    }

    private Authentication tryAuthenticate(ApplicationUser applicationUser) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        applicationUser.getUsername(),
                        applicationUser.getPassword()));
    }
}
