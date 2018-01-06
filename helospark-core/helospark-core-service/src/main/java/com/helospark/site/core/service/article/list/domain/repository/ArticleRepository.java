package com.helospark.site.core.service.article.list.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.helospark.site.core.service.article.categories.domain.ArticleCategory;
import com.helospark.site.core.service.article.list.domain.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Integer> {

    List<Article> findAllByCategoryOrderByCreationTimeDesc(ArticleCategory category, Pageable pageable);

}
