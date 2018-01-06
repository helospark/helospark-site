package com.helospark.site.core.service.article.categories.domain;

import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Generated("SparkTools")
    private ArticleCategory(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.iconClass = builder.iconClass;
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
        return Objects.equals(id, castOther.id) && Objects.equals(name, castOther.name) && Objects.equals(iconClass, castOther.iconClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iconClass);
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

        public ArticleCategory build() {
            return new ArticleCategory(this);
        }
    }

}
