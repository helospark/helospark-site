package com.helospark.site.core.web.article.comment.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.comment.domain.CommentVoteEntity;
import com.helospark.site.core.web.article.comment.domain.MyVoteResponse;

@Component
public class ArticleVoteConverter {

    public List<MyVoteResponse> convert(List<Integer> comments, List<CommentVoteEntity> result) {
        return comments.stream()
                .map(comment -> toResult(comment, result))
                .collect(Collectors.toList());
    }

    private MyVoteResponse toResult(Integer comment, List<CommentVoteEntity> result) {
        MyVoteResponse response = new MyVoteResponse();
        response.setCommentId(comment);
        response.setDirection(getValue(result, comment));
        return response;
    }

    private Integer getValue(List<CommentVoteEntity> result, Integer comment) {
        return result.stream()
                .filter(a -> a.getPrimaryKey().getCommentId().equals(comment))
                .findFirst()
                .map(a -> a.getDirection())
                .orElse(0);
    }

}
