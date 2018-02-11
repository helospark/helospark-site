package com.helospark.site.core.web.user;

import static com.helospark.site.core.web.security.JwtClaimConstants.AUTHORITY;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.web.user.domain.LoginResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/users")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("${security.jwt.secretKey}")
    private String secret;
    @Value("${security.jwt.expirationTime}")
    private Integer expirationTime;

    @RequestMapping("/login")
    public LoginResponse attemptAuthentication(@RequestBody ApplicationUser applicationUser) {
        Authentication authentication = tryAuthenticate(applicationUser);
        return successfulAuthentication(authentication);
    }

    private Authentication tryAuthenticate(ApplicationUser applicationUser) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        applicationUser.getUsername(),
                        applicationUser.getPassword()));
    }

    private LoginResponse successfulAuthentication(Authentication auth) {
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim(AUTHORITY, getAuthoritiesAsString(auth))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        return LoginResponse.builder()
                .withToken(token)
                .withExpirationTimeInSeconds(expirationTime)
                .build();
    }

    private String getAuthoritiesAsString(Authentication auth) {
        return auth.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));
    }
}
