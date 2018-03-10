package com.helospark.site.core.web.user.domain;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Token.Builder.class)
public class Token {
    private String token;
    private long expirationTimeInSeconds;

    @Generated("SparkTools")
    private Token(Builder builder) {
        this.token = builder.token;
        this.expirationTimeInSeconds = builder.expirationTimeInSeconds;
    }

    public String getToken() {
        return token;
    }

    public long getExpirationTimeInSeconds() {
        return expirationTimeInSeconds;
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
        private long expirationTimeInSeconds;

        private Builder() {
        }

        private Builder(Token token) {
            this.token = token.token;
            this.expirationTimeInSeconds = token.expirationTimeInSeconds;
        }

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withExpirationTimeInSeconds(long expirationTimeInSeconds) {
            this.expirationTimeInSeconds = expirationTimeInSeconds;
            return this;
        }

        public Token build() {
            return new Token(this);
        }
    }

}
