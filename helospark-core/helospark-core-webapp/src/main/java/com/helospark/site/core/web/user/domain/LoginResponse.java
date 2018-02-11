package com.helospark.site.core.web.user.domain;

import javax.annotation.Generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = LoginResponse.Builder.class)
public class LoginResponse {
    private String token;
    private long expirationTimeInSeconds;

    @Generated("SparkTools")
    private LoginResponse(Builder builder) {
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
    @JsonPOJOBuilder
    public static final class Builder {
        private String token;
        private long expirationTimeInSeconds;

        private Builder() {
        }

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withExpirationTimeInSeconds(long expirationTimeInSeconds) {
            this.expirationTimeInSeconds = expirationTimeInSeconds;
            return this;
        }

        public LoginResponse build() {
            return new LoginResponse(this);
        }
    }

}
