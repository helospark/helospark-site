package com.helospark.site.core.web.security;

import javax.annotation.Generated;

public class TokenUser {
    private String userName;

    @Generated("SparkTools")
    private TokenUser(Builder builder) {
        this.userName = builder.userName;
    }

    public String getUserName() {
        return userName;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(TokenUser tokenUser) {
        return new Builder(tokenUser);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String userName;

        private Builder() {
        }

        private Builder(TokenUser tokenUser) {
            this.userName = tokenUser.userName;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public TokenUser build() {
            return new TokenUser(this);
        }
    }

}
