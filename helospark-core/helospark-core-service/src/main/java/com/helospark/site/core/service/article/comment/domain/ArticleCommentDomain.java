package com.helospark.site.core.service.article.comment.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = ArticleCommentDomain.Builder.class)
public class ArticleCommentDomain {
    private Integer id;
    private Integer childComments;
    private String text;
    private ZonedDateTime commentTime;
    private Integer votes;
    private ArticleCommentUser commenter;

    @Generated("SparkTools")
    private ArticleCommentDomain(Builder builder) {
        this.id = builder.id;
        this.childComments = builder.childComments;
        this.text = builder.text;
        this.commentTime = builder.commentTime;
        this.votes = builder.votes;
        this.commenter = builder.commenter;
    }

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getChildComments() {
        return childComments;
    }

    public ZonedDateTime getCommentTime() {
        return commentTime;
    }

    public Integer getVotes() {
        return votes;
    }

    public ArticleCommentUser getCommenter() {
        return commenter;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ArticleCommentDomain)) {
            return false;
        }
        ArticleCommentDomain castOther = (ArticleCommentDomain) other;
        return Objects.equals(id, castOther.id) && Objects.equals(text, castOther.text) && Objects.equals(commentTime, castOther.commentTime)
                && Objects.equals(votes, castOther.votes) && Objects.equals(commenter, castOther.commenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, commentTime, votes, commenter);
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
    public static Builder builderFrom(ArticleCommentDomain articleCommentDomain) {
        return new Builder(articleCommentDomain);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Integer id;
        private Integer childComments;
        private String text;
        private ZonedDateTime commentTime;
        private Integer votes;
        private ArticleCommentUser commenter;

        private Builder() {
        }

        private Builder(ArticleCommentDomain articleCommentDomain) {
            this.id = articleCommentDomain.id;
            this.childComments = articleCommentDomain.childComments;
            this.text = articleCommentDomain.text;
            this.commentTime = articleCommentDomain.commentTime;
            this.votes = articleCommentDomain.votes;
            this.commenter = articleCommentDomain.commenter;
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withChildComments(Integer childComments) {
            this.childComments = childComments;
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

        public Builder withVotes(Integer votes) {
            this.votes = votes;
            return this;
        }

        public Builder withCommenter(ArticleCommentUser commenter) {
            this.commenter = commenter;
            return this;
        }

        public ArticleCommentDomain build() {
            return new ArticleCommentDomain(this);
        }
    }

}
