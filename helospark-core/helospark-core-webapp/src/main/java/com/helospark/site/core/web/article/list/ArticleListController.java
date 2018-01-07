package com.helospark.site.core.web.article.list;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.categories.CategoryNotFoundException;
import com.helospark.site.core.service.article.list.ArticleListService;
import com.helospark.site.core.service.article.list.domain.ArticleListEntry;

@RestController
@Validated
public class ArticleListController {
    private ArticleListService articleListService;

    public ArticleListController(ArticleListService articleListService) {
        this.articleListService = articleListService;
    }

    @RequestMapping(path = "/categories/{categoryName}/articles", produces = "application/json")
    public List<ArticleListEntry> getCategoryListEntries(
            @NotNull @NotEmpty @PathVariable("categoryName") String categoryName,
            @Nullable @Min(1) @RequestParam("page") Integer pageNumber) {
        return articleListService.getArticleListEntries(categoryName, Optional.ofNullable(pageNumber));
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleArticleNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}