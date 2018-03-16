package com.helospark.site.core.web.article.comment;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.article.comment.CommentVoteService;
import com.helospark.site.core.service.article.comment.domain.CommentVoteEntity;
import com.helospark.site.core.service.article.comment.repository.CommentVoteRepository;
import com.helospark.site.core.service.article.user.ApplicationUser;
import com.helospark.site.core.web.article.comment.converter.ArticleVoteConverter;
import com.helospark.site.core.web.article.comment.domain.MyVoteResponse;
import com.helospark.site.core.web.security.annotation.InjectLoggedInUser;

@RestController
public class ArticleCommentVoteController {
    private CommentVoteRepository commentVoteRepository;
    private ArticleVoteConverter voteConverter;
    private CommentVoteService voteService;

    public ArticleCommentVoteController(CommentVoteRepository commentVoteRepository, ArticleVoteConverter voteConverter,
            CommentVoteService voteService) {
        this.commentVoteRepository = commentVoteRepository;
        this.voteConverter = voteConverter;
        this.voteService = voteService;
    }

    @PostMapping("/comment/{commentId}/vote")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void voteForComment(@PathVariable("commentId") @NotNull Integer comment, @NotNull @RequestParam("number") Integer number,
            @InjectLoggedInUser ApplicationUser activeUser) {
        voteService.voteForComment(comment, number, activeUser);
    }

    @GetMapping("/comment/vote/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<MyVoteResponse> haveIVoted(@RequestParam("comments") @NotEmpty @NotNull List<Integer> comments,
            @InjectLoggedInUser ApplicationUser activeUser) {
        List<CommentVoteEntity> result = commentVoteRepository.findAllByPrimaryKeyUserAndPrimaryKeyCommentIdIn(activeUser, comments);
        return voteConverter.convert(comments, result);
    }

}
