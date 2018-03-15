package com.helospark.site.core.web.article.comment.domain;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ArticleCommentUser.Builder.class)
public class ArticleCommentUser {
    private Integer id;
    private String name;

    @Generated("SparkTools")
    private ArticleCommentUser(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(ArticleCommentUser articleCommentUser) {
        return new Builder(articleCommentUser);
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArticleCommentUser)) {
            return false;
        }
        ArticleCommentUser castOther = (ArticleCommentUser) other;
        return Objects.equals(id, castOther.id) && Objects.equals(name, castOther.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Generated("SparkTools")
    @JsonPOJOBuilder
    public static final class Builder {
        private Integer id;
        private String name;

        private Builder() {
        }

        private Builder(ArticleCommentUser articleCommentUser) {
            this.id = articleCommentUser.id;
            this.name = articleCommentUser.name;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public ArticleCommentUser build() {
            return new ArticleCommentUser(this);
        }
    }

}
