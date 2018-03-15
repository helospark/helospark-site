package com.helospark.site.core.web.article.comment;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.repository.CommentRepository;
import com.helospark.site.core.service.article.detail.exception.ArticleNotFoundException;
import com.helospark.site.core.service.article.domain.repository.ArticleRepository;
import com.helospark.site.core.web.article.comment.converter.CommentConverter;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentForm;
import com.helospark.site.core.web.security.TokenUser;

@RestController
@RequestMapping("/comment")
public class ArticleCommentController {
    private CommentConverter commentConverter;
    private CommentRepository repository;
    private CommentPageableFactory pageableFactory;
    private ArticleRepository articleRepository;

    public ArticleCommentController(CommentConverter commentConverter, CommentRepository repository, CommentPageableFactory pageableFactory,
            ArticleRepository articleRepository) {
        this.commentConverter = commentConverter;
        this.repository = repository;
        this.pageableFactory = pageableFactory;
        this.articleRepository = articleRepository;
    }

    @GetMapping
    public List<ArticleCommentDomain> getComments(@RequestParam("page") Integer page, @RequestParam("articleId") Integer articleId) {
        assertArticleExists(articleId);
        Pageable pageable = pageableFactory.createPageable(page);
        List<ArticleCommentEntity> articles = repository.findAllByArticleId(articleId, pageable);
        return commentConverter.convert(articles);
    }

    private void assertArticleExists(Integer articleId) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void postComment(@RequestBody @Valid ArticleCommentForm comment, @AuthenticationPrincipal TokenUser activeUser) {
        ArticleCommentEntity commentEntity = commentConverter.convert(comment, activeUser);
        repository.save(commentEntity);
    }

}
