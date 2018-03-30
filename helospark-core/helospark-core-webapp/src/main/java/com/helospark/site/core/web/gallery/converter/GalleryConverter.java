package com.helospark.site.core.web.gallery.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.helospark.site.core.service.gallery.ImageUrlFactory;
import com.helospark.site.core.service.gallery.domain.GalleryEntity;
import com.helospark.site.core.service.gallery.domain.GalleryImageEntity;
import com.helospark.site.core.service.image.repository.ImageEntity;
import com.helospark.site.core.web.gallery.response.GalleryImage;
import com.helospark.site.core.web.gallery.response.GalleryListEntryResponse;
import com.helospark.site.core.web.gallery.response.GetGalleryResponse;

@Component
public class GalleryConverter {
    private ImageUrlFactory imageUrlFactory;
    private int thumbnailImageWidth;
    private int largeImageWidth;
    private String galleryNotFoundImage;

    public GalleryConverter(ImageUrlFactory imageUrlFactory,
            @Value("${gallery.thumbnail.width}") int galleryThumbnailImageWidth,
            @Value("${gallery.large.width}") int largeImageWidth,
            @Value("${gallery.notfound.url}") String notFoundUrl) {
        this.imageUrlFactory = imageUrlFactory;
        this.thumbnailImageWidth = galleryThumbnailImageWidth;
        this.largeImageWidth = largeImageWidth;
        this.galleryNotFoundImage = notFoundUrl;
    }

    public List<GalleryListEntryResponse> convert(Iterable<GalleryEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> convert(entity))
                .collect(Collectors.toList());
    }

    public GalleryListEntryResponse convert(GalleryEntity entity) {
        return GalleryListEntryResponse.builder()
                .withId(entity.getId())
                .withThumbnailUrl(convertOptionalImage(entity.getThumbnail()))
                .build();
    }

    private String convertOptionalImage(Optional<ImageEntity> optionalImage) {
        return optionalImage
                .map(image -> imageUrlFactory.createImageUrl(image, thumbnailImageWidth))
                .orElse(galleryNotFoundImage);
    }

    public GetGalleryResponse convertToGalleryResponse(GalleryEntity gallery) {
        return GetGalleryResponse.builder()
                .withId(gallery.getId())
                .withDescription(gallery.getDescription())
                .withTitle(gallery.getDescription())
                .withImages(convertImages(gallery.getImages()))
                .build();
    }

    private List<GalleryImage> convertImages(List<GalleryImageEntity> images) {
        return images.stream()
                .map(image -> convertImage(image))
                .collect(Collectors.toList());
    }

    public GalleryImage convertImage(GalleryImageEntity image) {
        ImageEntity entity = image.getImageEntity();
        return GalleryImage.builder()
                .withTitle(image.getTitle())
                .withDescription(image.getDescription())
                .withThumbnailUrl(imageUrlFactory.createImageUrl(entity, thumbnailImageWidth))
                .withLargeUrl(imageUrlFactory.createImageUrl(entity, largeImageWidth))
                .withOriginalUrl(imageUrlFactory.createOriginalImageUrl(entity))
                .build();
    }

}
