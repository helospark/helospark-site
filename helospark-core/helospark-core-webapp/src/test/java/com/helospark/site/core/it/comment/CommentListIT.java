package com.helospark.site.core.it.comment;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.web.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentUser;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties" })
@Sql(scripts = "classpath:/comment-list.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class CommentListIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<ArticleCommentDomain>> RESPONSE_TYPE = new ParameterizedTypeReference<List<ArticleCommentDomain>>() {
    };

    @Test
    public void testGetComments(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        List<ArticleCommentDomain> expected = createExpected();

        // WHEN
        ResponseEntity<List<ArticleCommentDomain>> result = restTemplate.exchange("/comment?articleId=1&page=0", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(expected));
    }

    @Test
    public void testGetCommentsShouldReturn404WhenArticleIdIsNotFound(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> result = restTemplate.exchange("/comment?articleId=101&page=0", HttpMethod.GET, null, Void.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(404));
    }

    @Test
    public void testGetCommentsShouldReturn404EmptyListWhenCommentIsRequestedAfterLastPage(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleCommentDomain>> result = restTemplate.exchange("/comment?articleId=1&page=1", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getBody(), is(emptyList()));
    }

    private List<ArticleCommentDomain> createExpected() {
        ArticleCommentDomain firstComment = ArticleCommentDomain.builder()
                .withCommenter(createArticleCommentUser())
                .withId(1)
                .withText("comment 1")
                .withVotes(0)
                .withCommentTime(ZonedDateTime.of(2017, 01, 01, 23, 0, 1, 0, ZoneId.of("UTC")))
                .build();

        ArticleCommentDomain secondComment = ArticleCommentDomain.builder()
                .withCommenter(createArticleCommentUser())
                .withId(2)
                .withText("comment 2")
                .withVotes(0)
                .withCommentTime(ZonedDateTime.of(2017, 01, 02, 23, 0, 1, 0, ZoneId.of("UTC")))
                .build();

        return asList(secondComment, firstComment);
    }

    private ArticleCommentUser createArticleCommentUser() {
        return ArticleCommentUser.builder()
                .withId(1)
                .withName("user")
                .build();
    }
}
