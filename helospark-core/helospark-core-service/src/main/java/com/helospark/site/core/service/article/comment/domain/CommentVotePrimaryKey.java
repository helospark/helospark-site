package com.helospark.site.core.service.article.comment.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.springframework.context.annotation.Lazy;

import com.helospark.site.core.service.article.user.ApplicationUser;

@Embeddable
public class CommentVotePrimaryKey implements Serializable {
    @ManyToOne
    @Lazy
    private ApplicationUser user;

    // TODO: This is @ManyToOne relationship, not sure, how to represent, while keeping the integer type
    private Integer commentId;

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

}
