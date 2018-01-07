package com.helospark.site.core.service.article.categories.domain;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ArticleCategoryListEntry.Builder.class)
public class ArticleCategoryListEntry {
    private Integer id;
    private String name;
    private String iconClass;

    @Generated("SparkTools")
    private ArticleCategoryListEntry(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.iconClass = builder.iconClass;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconClass() {
        return iconClass;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArticleCategoryListEntry)) {
            return false;
        }
        ArticleCategoryListEntry castOther = (ArticleCategoryListEntry) other;
        return Objects.equals(id, castOther.id) && Objects.equals(name, castOther.name) && Objects.equals(iconClass, castOther.iconClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iconClass);
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
    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "with")
    public static final class Builder {
        private Integer id;
        private String name;
        private String iconClass;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withIconClass(String iconClass) {
            this.iconClass = iconClass;
            return this;
        }

        public ArticleCategoryListEntry build() {
            return new ArticleCategoryListEntry(this);
        }
    }

}
