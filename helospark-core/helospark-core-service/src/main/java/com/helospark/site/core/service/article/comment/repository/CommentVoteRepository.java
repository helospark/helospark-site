package com.helospark.site.core.service.article.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.article.comment.domain.CommentVoteEntity;
import com.helospark.site.core.service.article.comment.domain.CommentVotePrimaryKey;
import com.helospark.site.core.service.article.user.ApplicationUser;

@Repository
public interface CommentVoteRepository extends CrudRepository<CommentVoteEntity, CommentVotePrimaryKey> {

    List<CommentVoteEntity> findAllByPrimaryKeyUserAndPrimaryKeyCommentIdIn(ApplicationUser user, List<Integer> comment);

    @Query("select sum(commentVote.direction) from article_comment_vote commentVote where commentVote.primaryKey.commentId=?1")
    Integer sumByPrimaryKeyCommentId(Integer commentId);

}
