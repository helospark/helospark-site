package com.helospark.site.core.web.user.refresh;

import static com.helospark.site.core.web.security.JwtClaimConstants.PASSWORD_HASH;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.helospark.site.core.web.security.domain.AuthenticationResult;
import com.helospark.site.core.web.security.helper.AuthenticationService;
import com.helospark.site.core.web.security.helper.TokenToClaimConverter;
import com.helospark.site.core.web.user.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;

@Service
public class TokenRefreshService {
    private TokenToClaimConverter tokenToClaimConverter;
    private AuthenticationService authenticationService;
    private UserDetailsServiceImpl userDetailsService;

    public TokenRefreshService(TokenToClaimConverter tokenToClaimConverter, AuthenticationService authenticationService,
            UserDetailsServiceImpl userDetailsService) {
        this.tokenToClaimConverter = tokenToClaimConverter;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    public AuthenticationResult refreshToken(String refreshToken) {
        Claims claims = tokenToClaimConverter.convert(refreshToken);
        User user = loadUser(claims);
        assertCanIssueRefreshToken(claims, user);
        return authenticationService.successfulAuthentication(user);
    }

    private User loadUser(Claims claims) {
        try {
            return userDetailsService.loadUserByUsername(claims.getSubject());
        } catch (UsernameNotFoundException e) {
            throw new RefreshUnathorizedException("User account is no longer in database.");
        }
    }

    private void assertCanIssueRefreshToken(Claims claims, User user) {
        assertPasswordNotChanged(claims, user);
        assertUserAccountIsNotLocked(user);
    }

    private void assertPasswordNotChanged(Claims authentication, User user) {
        if (!user.getPassword().equals(authentication.get(PASSWORD_HASH))) {
            throw new RefreshUnathorizedException("Passwords do not match");
        }
    }

    private void assertUserAccountIsNotLocked(User user) {
        if (!(user.isAccountNonExpired() && user.isAccountNonLocked())) {
            throw new RefreshUnathorizedException("User account is not loginable");
        }
    }
}
