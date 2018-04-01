package com.helospark.site.core.web.security.helper;

import static com.helospark.site.core.web.security.JwtClaimConstants.AUTHORITY;
import static com.helospark.site.core.web.security.JwtClaimConstants.PASSWORD_HASH;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
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
    @Autowired
    private ApplicationUserRepository userRepository;

    public AuthenticationResult successfulAuthentication(User user) {
        ApplicationUser applicationUser = userRepository.findByUsername(user.getUsername());
        if (applicationUser == null) {
            throw new IllegalStateException("Login found, but user is not in the database");
        }
        Token authenticationToken = buildAuthenticationToken(user, applicationUser);
        Token refreshToken = buildRefreshToken(user);
        return AuthenticationResult.builder()
                .withAuthenticationToken(authenticationToken)
                .withRefreshToken(refreshToken)
                .withIsAdmin(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build();
    }

    private Token buildAuthenticationToken(User user, ApplicationUser applicationUser) {
        long expiresAt = System.currentTimeMillis() + authenticationTokenExpirationTime;
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", applicationUser.getId())
                .setExpiration(new Date(expiresAt))
                .claim(AUTHORITY, getAuthoritiesAsString(user))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        return buildToken(token, expiresAt, authenticationTokenExpirationTime);
    }

    private Token buildRefreshToken(User user) {
        long expiresAt = System.currentTimeMillis() + refreshTokenExpirationTime;
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(expiresAt))
                .claim(PASSWORD_HASH, user.getPassword())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
        return buildToken(token, expiresAt, refreshTokenExpirationTime);
    }

    private Token buildToken(String token, Long expiresAt, Long expirationTime) {
        return Token.builder()
                .withToken(token)
                .withExpiresAt(expiresAt)
                .withExpirationTime(expirationTime)
                .build();
    }

    private String getAuthoritiesAsString(User user) {
        return user.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.joining(","));
    }
}
