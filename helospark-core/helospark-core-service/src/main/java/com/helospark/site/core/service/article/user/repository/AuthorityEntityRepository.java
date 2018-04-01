package com.helospark.site.core.service.article.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.article.user.AuthorityEntity;

@Repository
public interface AuthorityEntityRepository extends JpaRepository<AuthorityEntity, Long> {

    AuthorityEntity findByRole(String role);

}
