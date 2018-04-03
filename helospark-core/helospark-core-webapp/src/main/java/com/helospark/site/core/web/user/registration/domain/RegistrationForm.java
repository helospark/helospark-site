package com.helospark.site.core.web.user.registration.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.helospark.site.core.web.common.field.EqualFields;
import com.helospark.site.core.web.common.password.ValidPassword;
import com.helospark.site.core.web.user.registration.validation.UniqueUsername;

@EqualFields(firstField = "password", secondField = "passwordAgain", message = "Passwords do not match")
public class RegistrationForm {
    @NotNull
    @Size(min = 4)
    @UniqueUsername
    private String username;
    @ValidPassword
    private String password;
    private String passwordAgain;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

}
