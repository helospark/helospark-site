package com.helospark.site.core.service.article.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.comment.converter.CommentDomainConverter;
import com.helospark.site.core.service.article.comment.converter.CommentPageableFactory;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.repository.CommentRepository;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private CommentDomainConverter commentDomainConverter;
    private CommentPageableFactory pageableFactory;
    private CommentVoteService voteService;

    public CommentService(CommentRepository commentRepository, CommentDomainConverter commentDomainConverter, CommentPageableFactory pageableFactory,
            CommentVoteService voteService) {
        this.commentRepository = commentRepository;
        this.commentDomainConverter = commentDomainConverter;
        this.pageableFactory = pageableFactory;
        this.voteService = voteService;
    }

    public List<ArticleCommentDomain> getComments(Integer page, Integer articleId) {
        Pageable pageable = pageableFactory.createPageable(page);
        List<ArticleCommentEntity> comments = commentRepository.findAllByArticleId(articleId, pageable);
        List<Integer> votes = getVotes(comments);
        return commentDomainConverter.convert(comments, votes);
    }

    private List<Integer> getVotes(List<ArticleCommentEntity> comments) {
        // TODO: this could be embedded in the DB to avoid the recalculations
        // but it has good cacheability (though in distributed system it may not)
        return comments.stream()
                .map(comment -> voteService.countVotes(comment))
                .collect(Collectors.toList());
    }

}
