package com.helospark.site.core.service.article.comment.domain;

import java.time.ZonedDateTime;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.context.annotation.Lazy;

import com.helospark.site.core.service.article.domain.Article;
import com.helospark.site.core.service.article.user.ApplicationUser;

@Entity(name = "article_comment")
public class ArticleCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    private ZonedDateTime commentTime;

    private Integer parentCommentId;

    @Lazy
    @ManyToOne
    private ApplicationUser commenter;

    @Lazy
    @ManyToOne
    private Article article;

    @Generated("SparkTools")
    private ArticleCommentEntity(Builder builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.commentTime = builder.commentTime;
        this.parentCommentId = builder.parentCommentId;
        this.commenter = builder.commenter;
        this.article = builder.article;
    }

    public ArticleCommentEntity() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCommentTime(ZonedDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public void setCommenter(ApplicationUser commenter) {
        this.commenter = commenter;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public ApplicationUser getCommenter() {
        return commenter;
    }

    public ZonedDateTime getCommentTime() {
        return commentTime;
    }

    public Article getArticle() {
        return article;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(ArticleCommentEntity articleCommentEntity) {
        return new Builder(articleCommentEntity);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Integer id;
        private String text;
        private ZonedDateTime commentTime;
        private Integer parentCommentId;
        private ApplicationUser commenter;
        private Article article;

        private Builder() {
        }

        private Builder(ArticleCommentEntity articleCommentEntity) {
            this.id = articleCommentEntity.id;
            this.text = articleCommentEntity.text;
            this.commentTime = articleCommentEntity.commentTime;
            this.parentCommentId = articleCommentEntity.parentCommentId;
            this.commenter = articleCommentEntity.commenter;
            this.article = articleCommentEntity.article;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withCommentTime(ZonedDateTime commentTime) {
            this.commentTime = commentTime;
            return this;
        }

        public Builder withParentCommentId(Integer parentCommentId) {
            this.parentCommentId = parentCommentId;
            return this;
        }

        public Builder withCommenter(ApplicationUser commenter) {
            this.commenter = commenter;
            return this;
        }

        public Builder withArticle(Article article) {
            this.article = article;
            return this;
        }

        public ArticleCommentEntity build() {
            return new ArticleCommentEntity(this);
        }
    }

}
