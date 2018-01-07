package com.helospark.site.core.service.article.categories.domain;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CategoryInformation.Builder.class)
public class CategoryInformation {
    private String title;
    private int numberOfPages;
    private String description;
    private int articlesPerPage;

    @Generated("SparkTools")
    private CategoryInformation(Builder builder) {
        this.title = builder.title;
        this.numberOfPages = builder.numberOfPages;
        this.description = builder.description;
        this.articlesPerPage = builder.articlesPerPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getDescription() {
        return description;
    }

    public int getArticlesPerPage() {
        return articlesPerPage;
    }

    public String getTitle() {
        return title;
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof CategoryInformation)) {
            return false;
        }
        CategoryInformation castOther = (CategoryInformation) other;
        return Objects.equals(numberOfPages, castOther.numberOfPages) && Objects.equals(description, castOther.description)
                && Objects.equals(articlesPerPage, castOther.articlesPerPage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfPages, description, articlesPerPage);
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    public static final class Builder {
        private String title;
        private int numberOfPages;
        private String description;
        private int articlesPerPage;

        private Builder() {
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withNumberOfPages(int numberOfPages) {
            this.numberOfPages = numberOfPages;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withArticlesPerPage(int articlesPerPage) {
            this.articlesPerPage = articlesPerPage;
            return this;
        }

        public CategoryInformation build() {
            return new CategoryInformation(this);
        }
    }

}
