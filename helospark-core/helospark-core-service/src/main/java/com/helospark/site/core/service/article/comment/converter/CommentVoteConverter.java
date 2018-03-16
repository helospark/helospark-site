package com.helospark.site.core.service.article.comment.converter;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.domain.CommentVoteEntity;
import com.helospark.site.core.service.article.comment.domain.CommentVotePrimaryKey;
import com.helospark.site.core.service.article.user.ApplicationUser;

@Component
public class CommentVoteConverter {

    public CommentVoteEntity convert(ArticleCommentEntity comment, Integer number, ApplicationUser activeUser) {
        return CommentVoteEntity.builder()
                .withPrimaryKey(createPrimaryKey(activeUser, comment))
                .withDirection(number)
                .build();
    }

    private CommentVotePrimaryKey createPrimaryKey(ApplicationUser activeUser, ArticleCommentEntity comment) {
        CommentVotePrimaryKey key = new CommentVotePrimaryKey();
        key.setCommentId(comment.getId());
        key.setUser(activeUser);
        return key;
    }

}
