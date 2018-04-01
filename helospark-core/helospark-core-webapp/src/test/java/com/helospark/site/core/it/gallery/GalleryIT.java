package com.helospark.site.core.it.gallery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.helospark.site.core.common.gallery.request.CreateGalleryRequest;
import com.helospark.site.core.common.gallery.response.CreateGalleryResponse;
import com.helospark.site.core.web.gallery.response.GalleryImage;
import com.helospark.site.core.web.gallery.response.GalleryListEntryResponse;
import com.helospark.site.core.web.gallery.response.GetGalleryResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@TestPropertySource(locations = { "classpath:global-it-overrides.properties", "classpath:gallery-it-overrides.properties" })
@Sql(scripts = "classpath:/list-path.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/clean-up.sql", executionPhase = AFTER_TEST_METHOD)
public class GalleryIT {
    // Why do you do this to me, Java?
    private static final ParameterizedTypeReference<List<GalleryListEntryResponse>> RESPONSE_TYPE = new ParameterizedTypeReference<List<GalleryListEntryResponse>>() {
    };

    @AfterEach
    public void cleanUp() {
        File file = new File("/tmp/testimages");
        if (file.exists()) {
            file.delete();
        }
    }

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
        assertThat(result.getBody(), containsInAnyOrder(expectedListBody("super-cool-images", "Super cool images"),
                expectedListBody("second-gallery", "Second gallery")));
    }

    @Test
    public void testUploadImage(@Autowired TestRestTemplate restTemplate)
            throws IOException {
        // GIVEN
        createGallery(restTemplate, "Super cool images");
        createGallery(restTemplate, "Second gallery");

        // WHEN
        ResponseEntity<String> result = uploadImage(restTemplate, String.class);

        // THEN
        assertThat(result.getStatusCodeValue(), is(200));
    }

    @Test
    public void testUploadedImage(@Autowired TestRestTemplate restTemplate)
            throws IOException {
        // GIVEN
        createGallery(restTemplate, "Super cool images");
        createGallery(restTemplate, "Second gallery");

        // WHEN
        ResponseEntity<GalleryImage> result = uploadImage(restTemplate, GalleryImage.class);

        // THEN
        assertThat(result.getBody(), is(expectedImage()));
    }

    @Test
    public void testGetGallery(@Autowired TestRestTemplate restTemplate)
            throws IOException {
        // GIVEN
        createGallery(restTemplate, "Super cool images");
        createGallery(restTemplate, "Second gallery");
        uploadImage(restTemplate, GalleryImage.class);

        // WHEN
        ResponseEntity<GetGalleryResponse> result = restTemplate.exchange("/gallery/super-cool-images", HttpMethod.GET, null,
                GetGalleryResponse.class);

        // THEN
        assertThat(result.getBody(), is(expectedGalleryResponse()));
    }

    @Test
    public void testGetImageFromGalleryShouldReturnValidImage(@Autowired TestRestTemplate restTemplate)
            throws IOException {
        // GIVEN
        createGallery(restTemplate, "Super cool images");
        uploadImage(restTemplate, GalleryImage.class);
        ResponseEntity<GetGalleryResponse> result = restTemplate.exchange("/gallery/super-cool-images", HttpMethod.GET, null,
                GetGalleryResponse.class);
        String imageUrl = result.getBody().getImages().get(0).getThumbnailUrl();

        // WHEN
        ResponseEntity<byte[]> imageBytes = restTemplate.getForEntity(imageUrl, byte[].class);

        // THEN
        assertThat(imageBytes.getStatusCodeValue(), is(200));
        assertThat(imageBytes.getHeaders().get("Content-type").get(0), is(IMAGE_JPEG_VALUE));
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes.getBody()));
        assertThat(bufferedImage.getWidth(), is(200));
    }

    private GetGalleryResponse expectedGalleryResponse() {
        return GetGalleryResponse.builder()
                .withId("super-cool-images")
                .withDescription("Long long description that just goes on and on")
                .withTitle("Super cool images")
                .withImages(Collections.singletonList(expectedImage()))
                .build();
    }

    private GalleryImage expectedImage() {
        return GalleryImage.builder()
                .withTitle("Super title")
                .withDescription("Super image description")
                .withThumbnailUrl("/images/super-title?width=200&height=150&hash=21ddc9df4942297d043034ac738ab58b8940a26769bf949d1dd1d33daea662d3")
                .withOriginalUrl("/images/super-title/original")
                .withLargeUrl("/images/super-title?width=800&height=600&hash=e6717287baf4997f740fb6d8bab5d1a90b46cfc7ad903133371fa444e6ee2a55")
                .build();
    }

    private <T> ResponseEntity<T> uploadImage(TestRestTemplate restTemplate, Class<T> responseType) throws IOException {
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        parts.add("file", new ClassPathResource("/testdata/gallery/testimage.jpg"));
        parts.add("title", "Super title");
        parts.add("description", "Super image description");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentDispositionFormData("file", "testfile.jpg");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);

        return restTemplate.exchange("/gallery/super-cool-images/image", POST, requestEntity, responseType);

    }

    private ResponseEntity<CreateGalleryResponse> createGallery(TestRestTemplate restTemplate, String title) {
        CreateGalleryRequest request = new CreateGalleryRequest();
        request.setTitle(title);
        request.setDescription("Long long description that just goes on and on");
        return restTemplate.postForEntity("/gallery", request, CreateGalleryResponse.class);
    }

    private CreateGalleryResponse expectedBody(String string) {
        return CreateGalleryResponse.builder()
                .withGalleryId(string)
                .build();
    }

    private GalleryListEntryResponse expectedListBody(String id, String title) {
        return GalleryListEntryResponse.builder()
                .withId(id)
                .withTitle(title)
                .withThumbnailUrl("/notfound.jpg")
                .build();
    }

}
