package com.helospark.site.core.it.detail;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.service.article.detail.domain.ArticleDetailDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties", "classpath:article-list-test.properties" })
@Sql(scripts = "classpath:/list-path.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class ArticleDetailIT {

    @Test
    public void testArticleDetailsHappyPath(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        ArticleDetailDto expected = createExpectedResult();

        // WHEN
        ArticleDetailDto result = restTemplate.getForObject("/categories/blog/articles/pretty-url-id-2", ArticleDetailDto.class);

        // THEN
        assertThat(result, is(expected));
    }

    @Test
    public void testArticleDetailsShouldReturn404WhenCategoryNotFound(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<ArticleDetailDto> result = restTemplate.getForEntity("/categories/NotFound/articles/pretty-url-id-2", ArticleDetailDto.class);

        // THEN
        assertThat(result.getStatusCode(), is(NOT_FOUND));
    }

    @Test
    public void testArticleDetailsShouldReturn404WhenArticleNotFound(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<ArticleDetailDto> result = restTemplate.getForEntity("/categories/blog/articles/NotFound", ArticleDetailDto.class);

        // THEN
        assertThat(result.getStatusCode(), is(NOT_FOUND));
    }

    private ArticleDetailDto createExpectedResult() {
        return ArticleDetailDto.builder()
                .withId(2)
                .withUrlId("pretty-url-id-2")
                .withTitle("Pretty id")
                .withCreationTime(ZonedDateTime.of(2016, 12, 31, 23, 0, 1, 0, ZoneId.of("UTC")))
                .withCategoryName("blog")
                .withModificationTime(ZonedDateTime.of(2017, 1, 1, 23, 0, 1, 0, ZoneId.of("UTC")))
                .withDescription("BlogEntry1")
                .withText("BlogEntry1")
                .build();
    }
}
