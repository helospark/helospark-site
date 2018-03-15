package com.helospark.site.core.web.article.comment.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.detail.exception.ArticleNotFoundException;
import com.helospark.site.core.service.article.domain.Article;
import com.helospark.site.core.service.article.domain.repository.ArticleRepository;
import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.service.article.user.repository.ApplicationUserRepository;
import com.helospark.site.core.service.common.CurrentTimeProvider;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentForm;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentUser;
import com.helospark.site.core.web.security.TokenUser;
import com.helospark.site.core.web.security.UnknownUserException;

@Component
public class CommentConverter {
    private ArticleRepository articleRepository;
    private CurrentTimeProvider currentTimeProvider;
    private ApplicationUserRepository userRepository;

    public CommentConverter(ArticleRepository articleRepository, CurrentTimeProvider currentTimeProvider, ApplicationUserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.currentTimeProvider = currentTimeProvider;
        this.userRepository = userRepository;
    }

    public ArticleCommentEntity convert(ArticleCommentForm form, TokenUser activeUser) {
        Article article = findArticle(form.getArticleId());
        return ArticleCommentEntity.builder()
                .withArticle(article)
                .withCommenter(getCommenter(activeUser))
                .withText(form.getText())
                .withCommentTime(currentTimeProvider.currentZonedDateTime())
                .build();
    }

    private ApplicationUser getCommenter(TokenUser activeUser) {
        return Optional.ofNullable(userRepository.findByUsername(activeUser.getUserName()))
                .orElseThrow(() -> new UnknownUserException("Cannot find user " + activeUser.getUserName()));
    }

    private Article findArticle(Integer articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

    public List<ArticleCommentDomain> convert(List<ArticleCommentEntity> articles) {
        return articles.stream()
                .map(article -> convert(article))
                .collect(Collectors.toList());
    }

    private ArticleCommentDomain convert(ArticleCommentEntity article) {
        return ArticleCommentDomain.builder()
                .withCommenter(convertCommenter(article.getCommenter()))
                .withCommentTime(article.getCommentTime())
                .withId(article.getId())
                .withText(article.getText())
                .withVotes(0)
                .build();
    }

    private ArticleCommentUser convertCommenter(ApplicationUser commenter) {
        return ArticleCommentUser.builder()
                .withId(commenter.getId())
                .withName(commenter.getUsername())
                .build();
    }

}
