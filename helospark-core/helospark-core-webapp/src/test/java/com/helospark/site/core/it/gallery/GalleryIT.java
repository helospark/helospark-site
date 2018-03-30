package com.helospark.site.core.it.gallery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

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

import com.helospark.site.core.common.gallery.request.CreateGalleryRequest;
import com.helospark.site.core.common.gallery.response.CreateGalleryResponse;
import com.helospark.site.core.web.gallery.response.GalleryListEntryResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties" })
@Sql(scripts = "classpath:/list-path.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class GalleryIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<GalleryListEntryResponse>> RESPONSE_TYPE = new ParameterizedTypeReference<List<GalleryListEntryResponse>>() {
    };

    @Test
    public void testCreateGalleryShouldReturn200(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<CreateGalleryResponse> result = createGallery(restTemplate, "Super cool images");

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    @Test
    public void testCreateGalleryShouldReturnExpectedResult(@Autowired TestRestTemplate restTemplate) {
        // GIVEN

        // WHEN
        ResponseEntity<CreateGalleryResponse> result = createGallery(restTemplate, "Super cool images");

        // THEN
        assertThat(result.getBody(), is(expectedBody("super-cool-images")));
    }

    @Test
    public void testReadAllGalleriesShouldReadSavedGalleries(@Autowired TestRestTemplate restTemplate) {
        // GIVEN
        createGallery(restTemplate, "Super cool images");
        createGallery(restTemplate, "Second gallery");

        // WHEN
        ResponseEntity<List<GalleryListEntryResponse>> result = restTemplate.exchange("/gallery", HttpMethod.GET, null, RESPONSE_TYPE);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody().size(), is(2));
        assertThat(result.getBody(), containsInAnyOrder(expectedListBody("super-cool-images"), expectedListBody("second-gallery")));
    }

    private ResponseEntity<CreateGalleryResponse> createGallery(TestRestTemplate restTemplate, String title) {
        CreateGalleryRequest request = new CreateGalleryRequest();
        request.setTitle(title);
        request.setDescription("Long long description that just goes on and on");
        ResponseEntity<CreateGalleryResponse> result = restTemplate.postForEntity("/gallery", request, CreateGalleryResponse.class);
        return result;
    }

    private CreateGalleryResponse expectedBody(String string) {
        return CreateGalleryResponse.builder()
                .withGalleryId(string)
                .build();
    }

    private GalleryListEntryResponse expectedListBody(String string) {
        return GalleryListEntryResponse.builder()
                .withId(string)
                .withThumbnailUrl("/notfound.jpg")
                .build();
    }

}
