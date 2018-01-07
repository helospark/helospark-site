package com.helospark.site.core.service.article.categories;

import static java.util.stream.StreamSupport.stream;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.categories.domain.ArticleCategoryListEntry;
import com.helospark.site.core.service.article.categories.repository.domain.ArticleCategory;

@Component
public class ArticleCategoryListEntryConverter {

    public List<ArticleCategoryListEntry> convert(Iterable<ArticleCategory> categories) {
        return stream(categories.spliterator(), false)
                .map(category -> convert(category))
                .collect(Collectors.toList());
    }

    private ArticleCategoryListEntry convert(ArticleCategory category) {
        return ArticleCategoryListEntry.builder()
                .withId(category.getId())
                .withName(category.getName())
                .withIconClass(category.getIconClass())
                .build();
    }

}
