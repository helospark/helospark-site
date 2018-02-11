package com.helospark.site.core.service.article.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.helospark.site.core.service.article.categories.repository.domain.ArticleCategory;
import com.helospark.site.core.service.article.domain.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Integer> {

    List<Article> findAllByCategoryOrderByCreationTimeDesc(ArticleCategory category, Pageable pageable);

    int countByCategory(ArticleCategory category);

    Optional<Article> findByCategoryAndUrlId(ArticleCategory category, String urlId);
}
