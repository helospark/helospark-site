package com.helospark.site.core.web.security;

import static com.helospark.site.core.web.security.SecurityConstants.HEADER_STRING;
import static com.helospark.site.core.web.security.SecurityConstants.TOKEN_PREFIX;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.helospark.site.core.web.security.helper.ClaimToAuthenticationConverter;
import com.helospark.site.core.web.security.helper.TokenToClaimConverter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private ClaimToAuthenticationConverter claimToAuthenticationConverter;
    private TokenToClaimConverter tokenToClaimConverter;

    public JWTAuthorizationFilter(AuthenticationManager authManager, ClaimToAuthenticationConverter tokenToAuthenticationConverter,
            TokenToClaimConverter tokenToClaimConverter) {
        super(authManager);
        this.claimToAuthenticationConverter = tokenToAuthenticationConverter;
        this.tokenToClaimConverter = tokenToClaimConverter;
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
                .map(tokenToClaimConverter::convert)
                .map(claimToAuthenticationConverter::convertToAuthentication)
                .orElse(null);
    }

}