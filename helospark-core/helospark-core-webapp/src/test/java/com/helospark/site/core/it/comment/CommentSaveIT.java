package com.helospark.site.core.it.comment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
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
import com.helospark.site.core.it.mock.auth.CustomWithMockUser;
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
@CustomWithMockUser
public class CommentSaveIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<ArticleCommentDomain>> RESPONSE_TYPE = new ParameterizedTypeReference<List<ArticleCommentDomain>>() {
    };
    private static final ZonedDateTime COMMENT_TIME = ZonedDateTime.of(2016, 12, 31, 23, 0, 1, 0, ZoneId.of("UTC"));

    @MockBean
    private CurrentTimeProvider currentTimeProvider;

    @Test
    public void testSaveCommentShouldReturn200(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> result = saveComment(restTemplate, Void.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    @Test
    public void testSaveCommentShouldReturnSavedComment(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<ArticleCommentDomain> result = saveComment(restTemplate, ArticleCommentDomain.class);

        // THEN
        assertThat(result.getBody().getId(), is(not(nullValue())));
        assertThat(result.getBody().getText(), is("New comment"));
    }

    @Test
    public void testSaveCommentShouldBeReturned(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        saveComment(restTemplate, Void.class);

        // WHEN
        ResponseEntity<List<ArticleCommentDomain>> result = restTemplate.exchange("/comment?articleId=2&page=0", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(expectedResult()));
    }

    private <T> ResponseEntity<T> saveComment(TestRestTemplate restTemplate, Class<T> responseType) {
        given(currentTimeProvider.currentZonedDateTime()).willReturn(COMMENT_TIME);
        ArticleCommentForm form = createForm();
        return restTemplate.postForEntity("/comment", form, responseType);
    }

    private ArticleCommentForm createForm() {
        ArticleCommentForm form = new ArticleCommentForm();
        form.setArticleId(2);
        form.setText("New comment");
        return form;
    }

    private List<ArticleCommentDomain> expectedResult() {
        ArticleCommentDomain comment = ArticleCommentDomain.builder()
                .withCommenter(createArticleCommentUser())
                .withId(5)
                .withText("New comment")
                .withVotes(0)
                .withChildComments(0)
                .withCommentTime(COMMENT_TIME)
                .build();
        return Collections.singletonList(comment);
    }

    private ArticleCommentUser createArticleCommentUser() {
        return ArticleCommentUser.builder()
                .withId(1)
                .withName("user")
                .build();
    }
}
