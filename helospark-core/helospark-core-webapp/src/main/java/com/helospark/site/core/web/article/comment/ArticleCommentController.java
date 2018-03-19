package com.helospark.site.core.web.article.comment;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.comment.CommentService;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.web.article.comment.converter.CommentConverter;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentForm;
import com.helospark.site.core.web.article.comment.domain.GetChildCommentsForm;
import com.helospark.site.core.web.article.comment.domain.GetCommentsForm;
import com.helospark.site.core.web.security.annotation.InjectLoggedInUser;

@RestController
@RequestMapping("/comment")
public class ArticleCommentController {
    private CommentConverter commentConverter;
    private CommentService commentService;

    public ArticleCommentController(CommentConverter commentConverter, CommentService commentService) {
        this.commentConverter = commentConverter;
        this.commentService = commentService;
    }

    @GetMapping
    public List<ArticleCommentDomain> getComments(@Valid GetCommentsForm form) {
        return commentService.getComments(form.getPage(), form.getArticleId());
    }

    @GetMapping("/{commentId}/replies")
    public List<ArticleCommentDomain> getChildComments(@Valid GetChildCommentsForm form) {
        return commentService.getChildComments(form.getCommentId());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ArticleCommentDomain postComment(@RequestBody @Valid ArticleCommentForm comment, @InjectLoggedInUser ApplicationUser activeUser) {
        ArticleCommentEntity commentEntity = commentConverter.convert(comment, activeUser);
        return commentService.saveComment(commentEntity);
    }

}
