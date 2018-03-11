package com.helospark.site.core.web.user.domain;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Token.Builder.class)
public class Token {
    private String token;
    private long expirationTime;
    private long expiresAt;

    @Generated("SparkTools")
    private Token(Builder builder) {
        this.token = builder.token;
        this.expirationTime = builder.expirationTime;
        this.expiresAt = builder.expiresAt;
    }

    public String getToken() {
        return token;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(Token token) {
        return new Builder(token);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String token;
        private long expirationTime;
        private long expiresAt;

        private Builder() {
        }

        private Builder(Token token) {
            this.token = token.token;
            this.expirationTime = token.expirationTime;
            this.expiresAt = token.expiresAt;
        }

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public Builder withExpiresAt(long expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Token build() {
            return new Token(this);
        }
    }

}
