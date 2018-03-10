package com.helospark.site.core.web.security.domain;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.helospark.site.core.web.user.domain.Token;

@JsonDeserialize(builder = AuthenticationResult.Builder.class)
public class AuthenticationResult {
    private Token authenticationToken;
    private Token refreshToken;

    @Generated("SparkTools")
    private AuthenticationResult(Builder builder) {
        this.authenticationToken = builder.authenticationToken;
        this.refreshToken = builder.refreshToken;
    }

    public Token getAuthenticationToken() {
        return authenticationToken;
    }

    public Token getRefreshToken() {
        return refreshToken;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(AuthenticationResult loginResponse) {
        return new Builder(loginResponse);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Token authenticationToken;
        private Token refreshToken;

        private Builder() {
        }

        private Builder(AuthenticationResult loginResponse) {
            this.authenticationToken = loginResponse.authenticationToken;
            this.refreshToken = loginResponse.refreshToken;
        }

        public Builder withAuthenticationToken(Token authenticationToken) {
            this.authenticationToken = authenticationToken;
            return this;
        }

        public Builder withRefreshToken(Token refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public AuthenticationResult build() {
            return new AuthenticationResult(this);
        }
    }

}
