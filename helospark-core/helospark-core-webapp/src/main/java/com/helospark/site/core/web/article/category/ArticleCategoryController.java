package com.helospark.site.core.web.article.category;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.springframework.http.CacheControl.maxAge;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.categories.CategoryService;
import com.helospark.site.core.service.article.categories.domain.ArticleCategoryListEntry;

@RestController
public class ArticleCategoryController {
    private CategoryService categoryService;

    public ArticleCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(path = "/categories", produces = "application/json")
    public ResponseEntity<List<ArticleCategoryListEntry>> getCategories() {
        List<ArticleCategoryListEntry> result = categoryService.retrieveCategories();
        return ResponseEntity.ok()
                .cacheControl(maxAge(1000, HOURS))
                .body(result);
    }

}
