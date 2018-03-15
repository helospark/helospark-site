package com.helospark.site.core.web.security.helper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.helospark.site.core.web.security.JwtClaimConstants;
import com.helospark.site.core.web.security.TokenUser;

import io.jsonwebtoken.Claims;

@Component
public class ClaimToAuthenticationConverter {

    public UsernamePasswordAuthenticationToken convertToAuthentication(Claims body) {
        String userName = body.getSubject();
        List<? extends GrantedAuthority> authorities = extractAuthorities(body);
        return new UsernamePasswordAuthenticationToken(createTokenUser(userName), null, authorities);
    }

    private TokenUser createTokenUser(String userName) {
        return TokenUser.builder()
                .withUserName(userName)
                .build();
    }

    private List<? extends GrantedAuthority> extractAuthorities(Claims body) {
        return Optional.ofNullable(body.get(JwtClaimConstants.AUTHORITY))
                .map(authorities -> String.valueOf(authorities))
                .map(authorities -> Stream.of(authorities.split(",")))
                .orElse(Stream.empty())
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }
}
