package com.helospark.site.core.web.security.domain;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.helospark.site.core.web.user.domain.Token;

@JsonDeserialize(builder = AuthenticationResult.Builder.class)
public class AuthenticationResult {
    private Token authenticationToken;
    private Token refreshToken;
    private boolean isAdmin;

    @Generated("SparkTools")
    private AuthenticationResult(Builder builder) {
        this.authenticationToken = builder.authenticationToken;
        this.refreshToken = builder.refreshToken;
        this.isAdmin = builder.isAdmin;
    }

    public Token getAuthenticationToken() {
        return authenticationToken;
    }

    public Token getRefreshToken() {
        return refreshToken;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Token authenticationToken;
        private Token refreshToken;
        private boolean isAdmin;

        private Builder() {
        }

        public Builder withAuthenticationToken(Token authenticationToken) {
            this.authenticationToken = authenticationToken;
            return this;
        }

        public Builder withRefreshToken(Token refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder withIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
            return this;
        }

        public AuthenticationResult build() {
            return new AuthenticationResult(this);
        }
    }

}
