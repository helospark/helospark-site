package com.helospark.site.core.web.article.comment;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.comment.CommentService;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.repository.CommentRepository;
import com.helospark.site.core.service.article.detail.exception.ArticleNotFoundException;
import com.helospark.site.core.service.article.domain.repository.ArticleRepository;
import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.web.article.comment.converter.CommentConverter;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentForm;
import com.helospark.site.core.web.security.annotation.InjectLoggedInUser;

@RestController
@RequestMapping("/comment")
public class ArticleCommentController {
    private CommentConverter commentConverter;
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private CommentService commentService;

    public ArticleCommentController(CommentConverter commentConverter, CommentRepository commentRepository, ArticleRepository articleRepository,
            CommentService commentService) {
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    @GetMapping
    public List<ArticleCommentDomain> getComments(@RequestParam("page") Integer page, @RequestParam("articleId") Integer articleId) {
        assertArticleExists(articleId);
        return commentService.getComments(page, articleId);
    }

    private void assertArticleExists(Integer articleId) {
        articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void postComment(@RequestBody @Valid ArticleCommentForm comment, @InjectLoggedInUser ApplicationUser activeUser) {
        ArticleCommentEntity commentEntity = commentConverter.convert(comment, activeUser);
        commentRepository.save(commentEntity);
    }

}
