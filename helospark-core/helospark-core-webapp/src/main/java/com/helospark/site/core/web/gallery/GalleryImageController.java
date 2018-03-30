package com.helospark.site.core.web.gallery;

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

    @PostMapping
    public GalleryImage uploadImage(@Valid GalleryImageUploadRequest request) {
        GalleryImageEntity image = galleryService.saveImage(request);
        return galleryConverter.convertImage(image);
    }

}
