package com.helospark.site.core.service.article.detail.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ArticleDetailDto {
    private Integer id;
    private String urlId;
    private String title;
    private ZonedDateTime creationTime;
    private String categoryName;
    private ZonedDateTime modificationTime;
    private String description;
    private String text;

    /**
     * For Jackson.
     */
    public ArticleDetailDto() {
    }

    @Generated("SparkTools")
    private ArticleDetailDto(Builder builder) {
        this.id = builder.id;
        this.urlId = builder.urlId;
        this.title = builder.title;
        this.creationTime = builder.creationTime;
        this.categoryName = builder.categoryName;
        this.modificationTime = builder.modificationTime;
        this.description = builder.description;
        this.text = builder.text;
    }

    public Integer getId() {
        return id;
    }

    public String getUrlId() {
        return urlId;
    }

    public String getTitle() {
        return title;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ZonedDateTime getModificationTime() {
        return modificationTime;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArticleDetailDto)) {
            return false;
        }
        ArticleDetailDto castOther = (ArticleDetailDto) other;
        return Objects.equals(id, castOther.id) && Objects.equals(urlId, castOther.urlId) && Objects.equals(title, castOther.title)
                && Objects.equals(creationTime, castOther.creationTime) && Objects.equals(categoryName, castOther.categoryName)
                && Objects.equals(modificationTime, castOther.modificationTime) && Objects.equals(description, castOther.description)
                && Objects.equals(text, castOther.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlId, title, creationTime, categoryName, modificationTime, description, text);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Integer id;
        private String urlId;
        private String title;
        private ZonedDateTime creationTime;
        private String categoryName;
        private ZonedDateTime modificationTime;
        private String description;
        private String text;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withUrlId(String urlId) {
            this.urlId = urlId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withCreationTime(ZonedDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder withCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder withModificationTime(ZonedDateTime modificationTime) {
            this.modificationTime = modificationTime;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public ArticleDetailDto build() {
            return new ArticleDetailDto(this);
        }
    }

}
