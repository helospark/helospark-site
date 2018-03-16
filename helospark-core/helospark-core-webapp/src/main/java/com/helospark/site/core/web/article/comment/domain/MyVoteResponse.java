package com.helospark.site.core.web.article.comment.domain;

import java.util.Objects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class MyVoteResponse {
    private Integer commentId;
    private Integer direction;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof MyVoteResponse)) {
            return false;
        }
        MyVoteResponse castOther = (MyVoteResponse) other;
        return Objects.equals(commentId, castOther.commentId) && Objects.equals(direction, castOther.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, direction);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
