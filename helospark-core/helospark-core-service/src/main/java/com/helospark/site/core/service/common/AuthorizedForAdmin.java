package com.helospark.site.core.service.common;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@PreAuthorize("hasRole(ROLE_ADMIN)")
public @interface AuthorizedForAdmin {

}
