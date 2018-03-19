package com.helospark.site.core.web.article.comment.domain;

import javax.validation.constraints.NotNull;

import com.helospark.site.core.web.common.comment.ValidCommentId;

public class GetChildCommentsForm {
    @NotNull
    @ValidCommentId
    Integer commentId;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

}
