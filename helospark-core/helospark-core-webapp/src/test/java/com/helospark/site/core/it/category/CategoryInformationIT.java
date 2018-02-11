package com.helospark.site.core.it.category;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

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

import com.helospark.site.core.service.article.categories.domain.CategoryInformation;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties", "classpath:article-list-test.properties" })
@Sql(scripts = "classpath:/list-path.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class CategoryInformationIT {

    @Test
    public void testCategoryInformationWithEmptyPages(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        CategoryInformation expected = CategoryInformation.builder()
                .withArticlesPerPage(2)
                .withNumberOfPages(0)
                .withDescription("Here you can read about electronics. I'm especially interested in high voltage and and green energy.")
                .withTitle("Articles of Electronics")
                .build();

        // WHEN
        ResponseEntity<CategoryInformation> result = restTemplate.getForEntity("/categories/electronics/information", CategoryInformation.class);

        // THEN
        assertThat(result.getBody(), is(expected));
    }

    @Test
    public void testCategoryInformationWithTwoPages(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        CategoryInformation expected = CategoryInformation.builder()
                .withArticlesPerPage(2)
                .withNumberOfPages(2)
                .withDescription("This is the place where I put things I don't know where to put.")
                .withTitle("Kinda a Technical blog")
                .build();

        // WHEN
        ResponseEntity<CategoryInformation> result = restTemplate.getForEntity("/categories/blog/information", CategoryInformation.class);

        // THEN
        assertThat(result.getBody(), is(expected));
    }

    @Test
    public void testCategoryInformationWithSinglePage(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        CategoryInformation expected = CategoryInformation.builder()
                .withArticlesPerPage(2)
                .withNumberOfPages(1)
                .withDescription(
                        "In this category you can read about computer realted stuff, most notably programming, CGI, Linux. But many more subjects will come.")
                .withTitle("Articles about Computers")
                .build();

        // WHEN
        ResponseEntity<CategoryInformation> result = restTemplate.getForEntity("/categories/computer/information", CategoryInformation.class);

        // THEN
        assertThat(result.getBody(), is(expected));
    }

    @Test
    public void testCategoryWithNotFoundArticle(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<CategoryInformation> result = restTemplate.getForEntity("/categories/notFound/information", CategoryInformation.class);

        // THEN
        assertThat(result.getStatusCode(), is(NOT_FOUND));
    }

    @Test
    public void testCategoryShouldBeCacheable(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<CategoryInformation> result = restTemplate.getForEntity("/categories/blog/information", CategoryInformation.class);

        // THEN
        assertThat(result.getHeaders().get("Cache-Control"), is(singletonList("max-age=3600000")));
    }
}
