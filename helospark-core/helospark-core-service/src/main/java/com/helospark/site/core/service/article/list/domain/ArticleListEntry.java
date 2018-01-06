package com.helospark.site.core.service.article.list.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ArticleListEntry {
    private String articleId;
    private ZonedDateTime creationTime;
    private String description;
    private String thumbnailUrl;
    private String title;

    @Generated("SparkTools")
    private ArticleListEntry(Builder builder) {
        this.articleId = builder.articleId;
        this.creationTime = builder.creationTime;
        this.description = builder.description;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.title = builder.title;
    }

    /**
     * For Jackson.
     */
    public ArticleListEntry() {

    }

    public String getArticleId() {
        return articleId;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArticleListEntry)) {
            return false;
        }
        ArticleListEntry castOther = (ArticleListEntry) other;
        return Objects.equals(articleId, castOther.articleId) && Objects.equals(creationTime, castOther.creationTime)
                && Objects.equals(description, castOther.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, creationTime, description);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String articleId;
        private ZonedDateTime creationTime;
        private String description;
        private String thumbnailUrl;
        private String title;

        private Builder() {
        }

        public Builder withArticleId(String articleId) {
            this.articleId = articleId;
            return this;
        }

        public Builder withCreationTime(ZonedDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ArticleListEntry build() {
            return new ArticleListEntry(this);
        }
    }
}
