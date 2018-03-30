package com.helospark.site.core.service.gallery;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.helospark.site.core.service.image.repository.ImageEntity;
import com.helospark.site.core.service.image.service.ImageResizerHashCalculator;

@Component
public class ImageUrlFactory {
    private ImageResizerHashCalculator hashCalculator;

    public ImageUrlFactory(ImageResizerHashCalculator hashCalculator) {
        this.hashCalculator = hashCalculator;
    }

    public String createImageUrl(ImageEntity entity, int width) {
        float scaleFactor = (float) width / entity.getWidth();
        int height = (int) (scaleFactor * entity.getHeight());
        return createImageUrl(entity.getId(), width, height);
    }

    public String createImageUrl(String imageId, int width, int height) {
        String hash = hashCalculator.getHash(imageId, width, height);
        return UriComponentsBuilder.fromPath("/images/{imageId}")
                .queryParam("width", width)
                .queryParam("height", height)
                .queryParam("hash", hash)
                .build(Map.of("imageId", imageId))
                .toString();
    }

    public String createOriginalImageUrl(ImageEntity entity) {
        return UriComponentsBuilder.fromPath("/images/{imageId}/original")
                .build(Map.of("imageId", entity.getId()))
                .toString();
    }
}