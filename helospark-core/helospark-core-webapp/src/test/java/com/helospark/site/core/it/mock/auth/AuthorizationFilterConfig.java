package com.helospark.site.core.it.mock.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthorizationFilterConfig {

    @Bean
    public FilterRegistrationBean<AuthorizationSettingFilter> mockAuthorizationBean() {
        FilterRegistrationBean<AuthorizationSettingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthorizationSettingFilter());
        bean.setOrder(0);
        return bean;
    }

    @Bean
    @Primary
    public PasswordEncoder noOpEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
