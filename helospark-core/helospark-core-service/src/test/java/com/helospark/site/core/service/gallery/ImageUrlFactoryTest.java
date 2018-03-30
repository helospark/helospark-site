package com.helospark.site.core.service.gallery;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.helospark.site.core.service.image.repository.ImageEntity;
import com.helospark.site.core.service.image.service.ImageResizerHashCalculator;

public class ImageUrlFactoryTest {
    @Mock
    private ImageResizerHashCalculator hashCalculator;

    private ImageUrlFactory urlFactory;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        urlFactory = new ImageUrlFactory(hashCalculator);
    }

    @Test
    public void test() {
        // GIVEN
        given(hashCalculator.getHash("image-id", 50, 50)).willReturn("hashCode");

        ImageEntity entity = new ImageEntity();
        entity.setId("image-id");
        entity.setWidth(100);
        entity.setHeight(100);

        // WHEN
        String result = urlFactory.createImageUrl(entity, 50);

        // THEN
        assertThat(result, is("/images/image-id?width=50&height=50&hash=hashCode"));
    }

}
