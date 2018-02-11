package com.helospark.site.core.web.security;

import static com.helospark.site.core.web.security.SecurityConstants.HEADER_STRING;
import static com.helospark.site.core.web.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private String jwtSecret;

    public JWTAuthorizationFilter(AuthenticationManager authManager, String secret) {
        super(authManager);
        this.jwtSecret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            res.setStatus(FORBIDDEN.value());
            return;
        }
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        return ofNullable(request.getHeader(HEADER_STRING))
                .map(this::parseClaim)
                .map(this::convertToAuthentication)
                .orElse(null);
    }

    private Claims parseClaim(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody();
    }

    private UsernamePasswordAuthenticationToken convertToAuthentication(Claims body) {
        String userName = body.getSubject();
        List<? extends GrantedAuthority> authorities = extractAuthorities(body);
        return new UsernamePasswordAuthenticationToken(userName, null, authorities);
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