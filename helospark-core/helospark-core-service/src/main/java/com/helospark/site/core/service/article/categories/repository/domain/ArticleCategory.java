package com.helospark.site.core.service.article.categories.repository.domain;

import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity(name = "article_category")
public class ArticleCategory {
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon_class")
    private String iconClass;

    @Column(name = "description")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Column(name = "title")
    @Basic(fetch = FetchType.LAZY)
    private String title;

    @Generated("SparkTools")
    private ArticleCategory(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.iconClass = builder.iconClass;
        this.description = builder.description;
        this.title = builder.title;
    }

    public ArticleCategory() {

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

    public String getDescription() {
        return description;
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
        if (!(other instanceof ArticleCategory)) {
            return false;
        }
        ArticleCategory castOther = (ArticleCategory) other;
        return Objects.equals(id, castOther.id) && Objects.equals(name, castOther.name) && Objects.equals(iconClass, castOther.iconClass)
                && Objects.equals(description, castOther.description) && Objects.equals(title, castOther.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iconClass, description, title);
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Integer id;
        private String name;
        private String iconClass;
        private String description;
        private String title;

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

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ArticleCategory build() {
            return new ArticleCategory(this);
        }
    }

}
