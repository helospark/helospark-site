package com.helospark.site.core.web.article.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.categories.domain.ArticleCategory;
import com.helospark.site.core.service.article.categories.repository.ArticleCategoryRepository;

@RestController
public class ArticleCategoryController {
    private ArticleCategoryRepository articleCategoryRepository;

    public ArticleCategoryController(ArticleCategoryRepository articleCategoryRepository) {
        this.articleCategoryRepository = articleCategoryRepository;
    }

    @RequestMapping(path = "/categories", produces = "application/json")
    public Iterable<ArticleCategory> getCategories() {
        return articleCategoryRepository.findAll();
    }

}
