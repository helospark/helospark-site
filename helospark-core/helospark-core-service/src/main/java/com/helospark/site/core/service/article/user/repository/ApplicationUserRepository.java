package com.helospark.site.core.service.article.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helospark.site.core.service.article.user.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);
}