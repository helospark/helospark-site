package com.helospark.site.core.service.article.comment.domain;

import javax.annotation.Generated;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "article_comment_vote")
public class CommentVoteEntity {

    @EmbeddedId
    private CommentVotePrimaryKey primaryKey;

    private Integer direction;

    @Generated("SparkTools")
    private CommentVoteEntity(Builder builder) {
        this.primaryKey = builder.primaryKey;
        this.direction = builder.direction;
    }

    private CommentVoteEntity() {

    }

    public CommentVotePrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(CommentVotePrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(CommentVoteEntity commentVoteEntity) {
        return new Builder(commentVoteEntity);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private CommentVotePrimaryKey primaryKey;
        private Integer direction;

        private Builder() {
        }

        private Builder(CommentVoteEntity commentVoteEntity) {
            this.primaryKey = commentVoteEntity.primaryKey;
            this.direction = commentVoteEntity.direction;
        }

        public Builder withPrimaryKey(CommentVotePrimaryKey primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public Builder withDirection(Integer direction) {
            this.direction = direction;
            return this;
        }

        public CommentVoteEntity build() {
            return new CommentVoteEntity(this);
        }
    }

}
