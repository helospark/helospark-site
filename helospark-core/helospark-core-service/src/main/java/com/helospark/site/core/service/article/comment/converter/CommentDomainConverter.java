package com.helospark.site.core.service.article.comment.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.helospark.site.core.service.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentEntity;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentUser;
import com.helospark.site.core.service.article.user.ApplicationUser;

@Component
public class CommentDomainConverter {

    public List<ArticleCommentDomain> convert(List<ArticleCommentEntity> articles, List<Integer> votes, List<Integer> childComments) {
        List<ArticleCommentDomain> result = new ArrayList<>();
        for (int i = 0; i < articles.size(); ++i) {
            result.add(convert(articles.get(i), votes.get(i), childComments.get(i)));
        }
        return result;
    }

    public ArticleCommentDomain convert(ArticleCommentEntity article, Integer votes, Integer childComments) {
        return ArticleCommentDomain.builder()
                .withCommenter(convertCommenter(article.getCommenter()))
                .withCommentTime(article.getCommentTime())
                .withId(article.getId())
                .withText(article.getText())
                .withVotes(votes)
                .withChildComments(childComments)
                .build();
    }

    private ArticleCommentUser convertCommenter(ApplicationUser commenter) {
        return ArticleCommentUser.builder()
                .withId(commenter.getId())
                .withName(commenter.getUsername())
                .build();
    }

}
