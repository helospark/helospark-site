package com.helospark.site.core.web.article.detail;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.detail.ArticleDetailService;
import com.helospark.site.core.service.article.detail.domain.ArticleDetailDto;

@RestController
public class ArticleDetailController {
    private ArticleDetailService articleDetailService;

    public ArticleDetailController(ArticleDetailService articleDetailService) {
        this.articleDetailService = articleDetailService;
    }

    @RequestMapping(path = "/categories/{categoryName}/articles/{articleName}", produces = "application/json")
    public ArticleDetailDto getArticleDetail(@PathVariable("categoryName") String category, @PathVariable("articleName") String article) {
        return articleDetailService.getArticleDetail(category, article);
    }

}
