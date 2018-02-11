package com.helospark.site.core.service.article.list;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.domain.Article;
import com.helospark.site.core.service.article.list.domain.ArticleListEntry;

@Component
public class ArticleListConverter {

    public List<ArticleListEntry> convertArticles(List<Article> articles) {
        return articles.stream()
                .map(article -> convert(article))
                .collect(Collectors.toList());
    }

    public ArticleListEntry convert(Article article) {
        return ArticleListEntry.builder()
                .withArticleId(article.getUrlId())
                .withCreationTime(article.getCreationTime())
                .withDescription(article.getDetails().getDescription())
                .withTitle(article.getTitle())
                .build();
    }

}
