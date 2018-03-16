package com.helospark.site.core.service.article.comment;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.comment.converter.CommentVoteConverter;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.domain.CommentVoteEntity;
import com.helospark.site.core.service.article.comment.repository.CommentRepository;
import com.helospark.site.core.service.article.comment.repository.CommentVoteRepository;
import com.helospark.site.core.service.article.user.ApplicationUser;

@Service
public class CommentVoteService {
    private CommentVoteRepository voteRepository;
    private CommentVoteConverter voteConverter;
    private CommentRepository commentRepostory;

    public CommentVoteService(CommentVoteRepository voteRepository, CommentVoteConverter voteConverter, CommentRepository commentRepostory) {
        this.voteRepository = voteRepository;
        this.voteConverter = voteConverter;
        this.commentRepostory = commentRepostory;
    }

    @Cacheable(cacheNames = "commentVoteCache")
    public Integer countVotes(ArticleCommentEntity comment) {
        return Optional.ofNullable(voteRepository.sumByPrimaryKeyCommentId(comment.getId()))
                .orElse(0);
    }

    public void voteForComment(Integer comment, @NotNull Integer number, ApplicationUser activeUser) {
        assertVoteNumberValid(number);
        ArticleCommentEntity foundComment = commentRepostory.findById(comment)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        CommentVoteEntity entity = voteConverter.convert(foundComment, number, activeUser);
        voteRepository.save(entity);
    }

    private void assertVoteNumberValid(Integer number) {
        if (!(number == -1 || number == 1)) {
            throw new VoteNotValidException("Vote number is invalid");
        }
    }
}
