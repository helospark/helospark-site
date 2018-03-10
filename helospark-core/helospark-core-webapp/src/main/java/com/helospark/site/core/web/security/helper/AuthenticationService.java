package com.helospark.site.core.web.security.helper;

import static com.helospark.site.core.web.security.JwtClaimConstants.AUTHORITY;
import static com.helospark.site.core.web.security.JwtClaimConstants.PASSWORD_HASH;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.helospark.site.core.web.security.domain.AuthenticationResult;
import com.helospark.site.core.web.user.domain.Token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService {
    @Value("${security.jwt.secretKey}")
    private String secret;
    @Value("${security.jwt.authentication.expirationTime}")
    private Long authenticationTokenExpirationTime;
    @Value("${security.jwt.refresh.expirationTime}")
    private Long refreshTokenExpirationTime;

    public AuthenticationResult successfulAuthentication(User user) {
        Token authenticationToken = buildAuthenticationToken(user);
        Token refreshToken = buildRefreshToken(user);
        return AuthenticationResult.builder()
                .withAuthenticationToken(authenticationToken)
                .withRefreshToken(refreshToken)
                .build();
    }

    private Token buildAuthenticationToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + authenticationTokenExpirationTime))
                .claim(AUTHORITY, getAuthoritiesAsString(user))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        return buildToken(token, authenticationTokenExpirationTime);
    }

    private Token buildRefreshToken(User user) {
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + authenticationTokenExpirationTime))
                .claim(PASSWORD_HASH, user.getPassword())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        return buildToken(token, refreshTokenExpirationTime);
    }

    private Token buildToken(String token, Long authenticationTokenExpirationTime2) {
        return Token.builder()
                .withToken(token)
                .withExpirationTimeInSeconds(authenticationTokenExpirationTime2)
                .build();
    }

    private String getAuthoritiesAsString(User user) {
        return user.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));
    }
}
