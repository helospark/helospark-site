package com.helospark.site.core.service.article.comment.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<ArticleCommentEntity, Integer> {

    List<ArticleCommentEntity> findAllByArticleId(Integer articleId, Pageable pageable);

}
