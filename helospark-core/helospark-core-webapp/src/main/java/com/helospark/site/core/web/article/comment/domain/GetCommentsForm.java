package com.helospark.site.core.web.article.comment.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.helospark.site.core.web.common.article.ValidArticleId;

public class GetCommentsForm {
    @NotNull
    @Min(0)
    private Integer page;

    @NotNull
    @ValidArticleId
    private Integer articleId;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

}
