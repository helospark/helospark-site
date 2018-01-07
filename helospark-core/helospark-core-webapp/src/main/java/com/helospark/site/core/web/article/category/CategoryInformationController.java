package com.helospark.site.core.web.article.category;

import static java.util.concurrent.TimeUnit.HOURS;
import static org.springframework.http.CacheControl.maxAge;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.categories.CategoryInformationService;
import com.helospark.site.core.service.article.categories.CategoryNotFoundException;
import com.helospark.site.core.service.article.categories.domain.CategoryInformation;

@RestController
public class CategoryInformationController {
    private CategoryInformationService categoryInformationService;

    public CategoryInformationController(CategoryInformationService categoryInformationService) {
        this.categoryInformationService = categoryInformationService;
    }

    @RequestMapping(path = "/categories/{categoryName}/information")
    public ResponseEntity<CategoryInformation> getCategoryInformation(@NotNull @NotEmpty @PathVariable("categoryName") String categoryName) {
        CategoryInformation result = categoryInformationService.retrieveCategoryInformation(categoryName);
        return ResponseEntity.ok()
                .cacheControl(maxAge(1000, HOURS))
                .body(result);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleArticleNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
