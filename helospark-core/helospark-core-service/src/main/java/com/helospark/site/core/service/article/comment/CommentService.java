package com.helospark.site.core.service.article.comment;

import java.util.ArrayList;
import java.util.List;

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
        List<ArticleCommentEntity> comments = commentRepository.findAllByArticleIdAndParentCommentIdIsNull(articleId, pageable);
        return convertComments(comments);
    }

    public List<ArticleCommentDomain> getChildComments(Integer commentId) {
        List<ArticleCommentEntity> comments = commentRepository.findAllByParentCommentId(commentId);
        return convertComments(comments);
    }

    public ArticleCommentDomain saveComment(ArticleCommentEntity commentEntity) {
        ArticleCommentEntity result = commentRepository.save(commentEntity);
        return convertComment(result);
    }

    private List<ArticleCommentDomain> convertComments(List<ArticleCommentEntity> comments) {
        List<ArticleCommentDomain> result = new ArrayList<>();
        for (ArticleCommentEntity commentEntity : comments) {
            ArticleCommentDomain converted = convertComment(commentEntity);
            result.add(converted);
        }
        return result;
    }

    private ArticleCommentDomain convertComment(ArticleCommentEntity commentEntity) {
        // TODO: this could be embedded in the DB to avoid the recalculations
        // but it has good cacheability (though in distributed system it may not)
        Integer votes = getVote(commentEntity);
        Integer childComments = getChildComment(commentEntity);
        ArticleCommentDomain converted = commentDomainConverter.convert(commentEntity, votes, childComments);
        return converted;
    }

    private Integer getVote(ArticleCommentEntity comment) {
        return voteService.countVotes(comment);
    }

    private Integer getChildComment(ArticleCommentEntity comment) {
        return this.commentRepository.countByParentCommentId(comment.getId());
    }

}
