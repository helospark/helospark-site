package com.helospark.site.core.service.article.comment;

import org.springframework.stereotype.Service;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.repository.CommentRepository;

@Service
public class CommentService {
    private CommentRepository repository;

    public void saveComment(ArticleCommentEntity entity) {
        repository.save(entity);
    }

}
