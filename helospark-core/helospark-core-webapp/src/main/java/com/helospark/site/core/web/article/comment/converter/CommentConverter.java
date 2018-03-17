package com.helospark.site.core.web.article.comment.converter;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.detail.exception.ArticleNotFoundException;
import com.helospark.site.core.service.article.domain.Article;
import com.helospark.site.core.service.article.domain.repository.ArticleRepository;
import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.common.CurrentTimeProvider;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentForm;

@Component
public class CommentConverter {
    private ArticleRepository articleRepository;
    private CurrentTimeProvider currentTimeProvider;

    public CommentConverter(ArticleRepository articleRepository, CurrentTimeProvider currentTimeProvider) {
        this.articleRepository = articleRepository;
        this.currentTimeProvider = currentTimeProvider;
    }

    public ArticleCommentEntity convert(ArticleCommentForm form, ApplicationUser activeUser) {
        Article article = findArticle(form.getArticleId());
        return ArticleCommentEntity.builder()
                .withArticle(article)
                .withParentCommentId(form.getParentCommentId())
                .withCommenter(activeUser)
                .withText(form.getText())
                .withCommentTime(currentTimeProvider.currentZonedDateTime())
                .build();
    }

    private Article findArticle(Integer articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

}
