package com.helospark.site.core.service.article.list;

import static java.util.Optional.ofNullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.categories.CategoryNotFoundException;
import com.helospark.site.core.service.article.categories.repository.ArticleCategoryRepository;
import com.helospark.site.core.service.article.categories.repository.domain.ArticleCategory;
import com.helospark.site.core.service.article.domain.repository.ArticleRepository;
import com.helospark.site.core.service.article.list.domain.ArticleListEntry;

@Service
public class ArticleListService {
    private ArticleRepository articleRepository;
    private ArticleCategoryRepository articleCategoryRepository;
    private ArticleListConverter articleListConverter;
    private Integer pageSize;

    public ArticleListService(ArticleRepository articleRepository, ArticleCategoryRepository articleCategoryRepository,
            ArticleListConverter articleListConverter, @Value("${article.list.pagesize}") Integer pageSize) {
        this.articleRepository = articleRepository;
        this.articleCategoryRepository = articleCategoryRepository;
        this.articleListConverter = articleListConverter;
        this.pageSize = pageSize;
    }

    public List<ArticleListEntry> getArticleListEntries(String categoryName, Optional<Integer> page) {
        Optional<ArticleCategory> articleCategory = articleCategoryRepository.findByName(categoryName);
        ArticleCategory category = articleCategory
                .orElseThrow(() -> new CategoryNotFoundException("Article with name " + categoryName + " not found"));

        return ofNullable(articleRepository.findAllByCategoryOrderByCreationTimeDesc(category, createPageable(page)))
                .map(articles -> articleListConverter.convertArticles(articles))
                .orElse(Collections.emptyList());
    }

    private Pageable createPageable(Optional<Integer> page) {
        int actualPage = page
                .map(pageNumber -> pageNumber - 1)
                .orElse(0);
        return PageRequest.of(actualPage, pageSize);
    }
}
