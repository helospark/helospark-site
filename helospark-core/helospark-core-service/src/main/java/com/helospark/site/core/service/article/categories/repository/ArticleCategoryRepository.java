package com.helospark.site.core.service.article.categories.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.article.categories.domain.ArticleCategory;

@Repository
public interface ArticleCategoryRepository extends CrudRepository<ArticleCategory, Integer> {

    public Optional<ArticleCategory> findByName(String name);

}
