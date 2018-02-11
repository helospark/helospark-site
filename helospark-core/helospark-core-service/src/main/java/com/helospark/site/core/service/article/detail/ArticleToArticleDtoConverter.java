package com.helospark.site.core.service.article.detail;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.detail.domain.ArticleDetailDto;
import com.helospark.site.core.service.article.domain.Article;

@Component
public class ArticleToArticleDtoConverter {

    public ArticleDetailDto convert(Article article) {
        return ArticleDetailDto.builder()
                .withCategoryName(article.getCategory().getName())
                .withCreationTime(article.getCreationTime())
                .withDescription(article.getDetails().getDescription())
                .withId(article.getId())
                .withModificationTime(article.getDetails().getModificationTime())
                .withText(convertLobToString(article.getDetails().getData()))
                .withTitle(article.getTitle())
                .withUrlId(article.getUrlId())
                .build();
    }

    private String convertLobToString(byte[] data) {
        return new String(data, UTF_8);
    }

}
