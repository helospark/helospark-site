package com.helospark.site.core.it.comment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.Arrays;
import java.util.Collections;
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

import com.helospark.site.core.Application;
import com.helospark.site.core.it.mock.auth.AuthorizationFilterConfig;
import com.helospark.site.core.it.mock.auth.CustomWithMockUser;
import com.helospark.site.core.web.article.comment.domain.MyVoteResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, AuthorizationFilterConfig.class })
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties" })
@Sql(scripts = "classpath:/comment-list.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
@CustomWithMockUser
public class CommentVoteIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<MyVoteResponse>> RESPONSE_TYPE = new ParameterizedTypeReference<List<MyVoteResponse>>() {
    };

    @Test
    public void testVoteForComment(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> response = restTemplate.postForEntity("/comment/1/vote?number=1", null, Void.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(200));
    }

    @Test
    public void testVoteForCommentWithWrongCommentId(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> response = restTemplate.postForEntity("/comment/101/vote?number=1", null, Void.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(404));
    }

    @Test
    public void testVoteForCommentWithWrongNumber(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> response = restTemplate.postForEntity("/comment/101/vote?number=2", null, Void.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(400));
    }

    @Test
    public void testVoteForCommentWithWrongNumber2(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> response = restTemplate.postForEntity("/comment/101/vote?number=-2", null, Void.class);

        // THEN
        assertThat(response.getStatusCodeValue(), is(400));
    }

    @Test
    public void testVoteForThenQueryMyVotes(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        restTemplate.postForEntity("/comment/1/vote?number=1", null, Void.class);

        // WHEN
        ResponseEntity<List<MyVoteResponse>> result = restTemplate.exchange("/comment/vote/me?comments=1,2", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(expectedResultWithDoubleVote()));
    }

    @Test
    public void testVoteThenUpdateVote(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        restTemplate.postForEntity("/comment/1/vote?number=1", null, Void.class);
        restTemplate.postForEntity("/comment/1/vote?number=-1", null, Void.class);

        // WHEN
        ResponseEntity<List<MyVoteResponse>> result = restTemplate.exchange("/comment/vote/me?comments=1", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(expectedResultWithChangedVote()));
    }

    @Test
    public void testQueryWithNullParameter(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<Void> result = restTemplate.exchange("/comment/vote/me", HttpMethod.GET, null, Void.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(400));
    }

    private List<MyVoteResponse> expectedResultWithDoubleVote() {
        MyVoteResponse response = new MyVoteResponse();
        response.setCommentId(1);
        response.setDirection(1);

        MyVoteResponse response2 = new MyVoteResponse();
        response2.setCommentId(2);
        response2.setDirection(0);
        return Arrays.asList(response, response2);
    }

    private List<MyVoteResponse> expectedResultWithChangedVote() {
        MyVoteResponse response = new MyVoteResponse();
        response.setCommentId(1);
        response.setDirection(-1);
        return Collections.singletonList(response);
    }
}
