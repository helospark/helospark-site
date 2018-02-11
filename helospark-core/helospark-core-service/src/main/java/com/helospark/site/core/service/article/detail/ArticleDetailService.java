package com.helospark.site.core.service.article.detail;

import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.categories.repository.ArticleCategoryRepository;
import com.helospark.site.core.service.article.categories.repository.domain.ArticleCategory;
import com.helospark.site.core.service.article.detail.domain.ArticleDetailDto;
import com.helospark.site.core.service.article.detail.exception.ArticleNotFoundException;
import com.helospark.site.core.service.article.domain.Article;
import com.helospark.site.core.service.article.domain.repository.ArticleRepository;

@Service
public class ArticleDetailService {
    private ArticleRepository articleRepository;
    private ArticleCategoryRepository categoryRepository;
    private ArticleToArticleDtoConverter articleToArticleDtoConverter;

    public ArticleDetailService(ArticleRepository articleRepository, ArticleCategoryRepository categoryRepository,
            ArticleToArticleDtoConverter articleToArticleDtoConverter) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.articleToArticleDtoConverter = articleToArticleDtoConverter;
    }

    public ArticleDetailDto getArticleDetail(String categoryName, String articleName) {
        ArticleCategory category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new ArticleNotFoundException("Category with " + categoryName + " not found"));
        Article article = articleRepository.findByCategoryAndUrlId(category, articleName)
                .orElseThrow(() -> new ArticleNotFoundException("Article with name " + articleName + " is not found in category " + category));
        return articleToArticleDtoConverter.convert(article);
    }
}
