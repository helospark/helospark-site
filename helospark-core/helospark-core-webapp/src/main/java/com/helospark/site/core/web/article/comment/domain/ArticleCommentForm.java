package com.helospark.site.core.web.article.comment.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

public class ArticleCommentForm {
    @NotNull
    private Integer articleId;

    @NotNull
    @NotEmpty
    @Size(max = 1000)
    private String text;

    @Nullable
    private Integer parentCommentId;

    public Integer getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Integer parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public String getText() {
        return text;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public void setText(String text) {
        this.text = text;
    }

}
