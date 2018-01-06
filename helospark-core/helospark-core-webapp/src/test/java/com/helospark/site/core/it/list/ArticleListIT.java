package com.helospark.site.core.it.list;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.service.article.list.domain.ArticleListEntry;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties", "classpath:article-list-test.properties" })
@Sql(scripts = "classpath:/list-path.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class ArticleListIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<ArticleListEntry>> RESPONSE_TYPE = new ParameterizedTypeReference<List<ArticleListEntry>>() {
    };

    @Test
    public void testArticleListShouldReturnExpectedResult(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        List<ArticleListEntry> expected = List.of(
                ArticleListEntry.builder()
                        .withArticleId("pretty-url-id")
                        .withCreationTime(ZonedDateTime.parse("2016-12-31T23:00:01Z[UTC]", DateTimeFormatter.ISO_ZONED_DATE_TIME))
                        .withDescription("description").build());

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/computer", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertAll(() -> assertThat(result.getStatusCode(), is(HttpStatus.OK)),
                () -> assertThat(result.getBody(), is(expected)));
    }

    @Test
    public void testArticleListResultWhenCalledWithNotExistentCategory(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/notExisting", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testArticleListResultWhenPageIsZeroShouldResultInBadRequest(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/blog?page=0", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testArticleLisWithEmptyCategoryList(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/electronics", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertAll(() -> assertThat(result.getStatusCode(), is(HttpStatus.OK)),
                () -> assertThat(result.getBody(), is(emptyList())));
    }

    @Test
    public void testArticleListWithPagingWithoutSpecifyingPageParameter(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/blog", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertAll(() -> assertThat(result.getStatusCode(), is(HttpStatus.OK)),
                () -> assertThat(result.getBody().size(), is(2)));
    }

    @Test
    public void testArticleListWithPagingExplicitPageOne(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/blog?page=1", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertAll(() -> assertThat(result.getStatusCode(), is(HttpStatus.OK)),
                () -> assertThat(result.getBody().size(), is(2)));
    }

    @Test
    public void testArticleListWithPagingExplicitPageTwo(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleListEntry>> result = restTemplate.exchange("/categories/blog?page=2", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertAll(() -> assertThat(result.getStatusCode(), is(HttpStatus.OK)),
                () -> assertThat(result.getBody().size(), is(1)));
    }

}
