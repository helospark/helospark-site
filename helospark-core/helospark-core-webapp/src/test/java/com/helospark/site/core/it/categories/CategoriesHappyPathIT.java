package com.helospark.site.core.it.categories;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.helospark.site.core.service.article.categories.domain.ArticleCategoryListEntry;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = "classpath:global-it-overrides.properties")
public class CategoriesHappyPathIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<ArticleCategoryListEntry>> RESPONSE_TYPE = new ParameterizedTypeReference<List<ArticleCategoryListEntry>>() {
    };

    @Test
    public void testCategoriesResult(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        List<ArticleCategoryListEntry> categories = List.of(
                ArticleCategoryListEntry.builder().withId(1).withName("computer").withIconClass("fa-laptop").build(),
                ArticleCategoryListEntry.builder().withId(2).withName("electronics").withIconClass("fa-plug").build(),
                ArticleCategoryListEntry.builder().withId(3).withName("blog").withIconClass("fa-rss-square").build());
        // WHEN
        ResponseEntity<List<ArticleCategoryListEntry>> result = restTemplate.exchange("/categories", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertAll(() -> assertThat(result.getStatusCode(), is(HttpStatus.OK)),
                () -> assertThat(result.getBody(), is(categories)));
    }

    @Test
    public void testCategoryShouldBeCacheable(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<List<ArticleCategoryListEntry>> result = restTemplate.exchange("/categories", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getHeaders().get("Cache-Control"), is(singletonList("max-age=3600000")));
    }
}
