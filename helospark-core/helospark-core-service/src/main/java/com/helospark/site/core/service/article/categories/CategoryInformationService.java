package com.helospark.site.core.service.article.categories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.categories.domain.CategoryInformation;
import com.helospark.site.core.service.article.categories.repository.ArticleCategoryRepository;
import com.helospark.site.core.service.article.categories.repository.domain.ArticleCategory;
import com.helospark.site.core.service.article.list.domain.repository.ArticleRepository;

@Service
public class CategoryInformationService {
    private ArticleCategoryRepository categoryRepository;
    private ArticleRepository articleRepository;
    private Integer articlesPerPage;

    public CategoryInformationService(ArticleCategoryRepository categoryRepository, ArticleRepository articleRepository,
            @Value("${article.list.pagesize}") Integer articlesPerPage) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
        this.articlesPerPage = articlesPerPage;
    }

    public CategoryInformation retrieveCategoryInformation(String categoryName) {
        ArticleCategory category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found " + categoryName));
        int numberOfArticles = articleRepository.countByCategory(category);

        return CategoryInformation.builder()
                .withArticlesPerPage(articlesPerPage)
                .withDescription(category.getDescription())
                .withNumberOfPages(calculatePageNumber(numberOfArticles))
                .withTitle(category.getTitle())
                .build();
    }

    private int calculatePageNumber(int numberOfArticles) {
        return (int) Math.ceil((double) numberOfArticles / articlesPerPage);
    }
}
