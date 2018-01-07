package com.helospark.site.core.service.article.categories;

import java.util.List;

import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.categories.domain.ArticleCategoryListEntry;
import com.helospark.site.core.service.article.categories.repository.ArticleCategoryRepository;
import com.helospark.site.core.service.article.categories.repository.domain.ArticleCategory;

@Service
public class CategoryService {
    private ArticleCategoryRepository categoryRepository;
    private ArticleCategoryListEntryConverter categoryConverter;

    public CategoryService(ArticleCategoryRepository categoryRepository, ArticleCategoryListEntryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    public List<ArticleCategoryListEntry> retrieveCategories() {
        Iterable<ArticleCategory> categories = categoryRepository.findAll();
        return categoryConverter.convert(categories);
    }
}
