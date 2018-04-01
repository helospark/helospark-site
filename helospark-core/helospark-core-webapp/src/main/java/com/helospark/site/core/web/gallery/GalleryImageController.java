package com.helospark.site.core.web.gallery;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.service.gallery.GalleryService;
import com.helospark.site.core.service.gallery.domain.GalleryImageEntity;
import com.helospark.site.core.web.gallery.converter.GalleryConverter;
import com.helospark.site.core.web.gallery.request.GalleryImageUploadRequest;
import com.helospark.site.core.web.gallery.response.GalleryImage;

@RestController
@RequestMapping("/gallery/{galleryId}/image")
public class GalleryImageController {
    private GalleryService galleryService;
    private GalleryConverter galleryConverter;

    public GalleryImageController(GalleryService galleryService, GalleryConverter galleryConverter) {
        this.galleryService = galleryService;
        this.galleryConverter = galleryConverter;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public GalleryImage uploadImage(@Valid GalleryImageUploadRequest request) {
        GalleryImageEntity image = galleryService.saveImage(request);
        return galleryConverter.convertImage(image);
    }

}
