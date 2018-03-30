package com.helospark.site.core.it.comment;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.Application;
import com.helospark.site.core.it.mock.auth.AuthorizationFilterConfig;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentDomain;
import com.helospark.site.core.service.article.comment.domain.ArticleCommentUser;
import com.helospark.site.core.service.common.CurrentTimeProvider;
import com.helospark.site.core.web.article.comment.domain.ArticleCommentForm;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, AuthorizationFilterConfig.class })
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties" })
@Sql(scripts = "classpath:/comment-list.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class CommentRepliesIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<ArticleCommentDomain>> RESPONSE_TYPE = new ParameterizedTypeReference<List<ArticleCommentDomain>>() {
    };
    private static final ZonedDateTime COMMENT_TIME = ZonedDateTime.of(2016, 12, 31, 23, 0, 1, 0, ZoneId.of("UTC"));

    @MockBean
    private CurrentTimeProvider currentTimeProvider;

    @Test
    public void testSaveCommentsToChild(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> result = saveComment(restTemplate, 1);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    @Test
    public void testSaveCommentsToChildAndGetArticleComment(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        saveComment(restTemplate, 1);

        // WHEN
        ResponseEntity<List<ArticleCommentDomain>> result = restTemplate.exchange("/comment?articleId=1&page=0", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(createExpected()));
    }

    @Test
    public void testSaveCommentsToChildAndGetCommentReplies(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        saveComment(restTemplate, 1);

        // WHEN
        ResponseEntity<List<ArticleCommentDomain>> result = restTemplate.exchange("/comment/1/replies", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(createExpectedReplies()));
    }

    @Test
    public void testSaveCommentsWithWrongParentCommentId(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> result = saveComment(restTemplate, 101);

        // THEN
        assertThat(result.getStatusCodeValue(), is(400));
    }

    private Object createExpectedReplies() {
        ArticleCommentDomain replyComment = ArticleCommentDomain.builder()
                .withCommenter(createArticleCommentUser())
                .withId(7)
                .withText("New comment")
                .withVotes(0)
                .withChildComments(0)
                .withCommentTime(COMMENT_TIME)
                .build();
        return Collections.singletonList(replyComment);
    }

    private List<ArticleCommentDomain> createExpected() {
        ArticleCommentDomain firstComment = ArticleCommentDomain.builder()
                .withCommenter(createArticleCommentUser())
                .withId(1)
                .withText("comment 1")
                .withVotes(0)
                .withChildComments(1)
                .withCommentTime(ZonedDateTime.of(2017, 01, 01, 23, 0, 1, 0, ZoneId.of("UTC")))
                .build();

        ArticleCommentDomain secondComment = ArticleCommentDomain.builder()
                .withCommenter(createArticleCommentUser())
                .withId(2)
                .withText("comment 2")
                .withVotes(0)
                .withChildComments(0)
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

    private ResponseEntity<Void> saveComment(TestRestTemplate restTemplate, int parentCommentId) {
        given(currentTimeProvider.currentZonedDateTime()).willReturn(COMMENT_TIME);
        ArticleCommentForm form = createForm(parentCommentId);
        ResponseEntity<Void> result = restTemplate.postForEntity("/comment", form, Void.class);
        return result;
    }

    private ArticleCommentForm createForm(int parentCommentId) {
        ArticleCommentForm form = new ArticleCommentForm();
        form.setArticleId(2);
        form.setText("New comment");
        form.setParentCommentId(parentCommentId);
        return form;
    }
}
