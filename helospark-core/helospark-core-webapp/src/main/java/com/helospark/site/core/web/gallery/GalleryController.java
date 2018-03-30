package com.helospark.site.core.web.gallery;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.common.gallery.request.CreateGalleryRequest;
import com.helospark.site.core.common.gallery.response.CreateGalleryResponse;
import com.helospark.site.core.service.gallery.GalleryService;
import com.helospark.site.core.service.gallery.domain.GalleryEntity;
import com.helospark.site.core.web.gallery.converter.GalleryConverter;
import com.helospark.site.core.web.gallery.response.GalleryListEntryResponse;
import com.helospark.site.core.web.gallery.response.GetGalleryResponse;

@RestController
@RequestMapping("/gallery")
public class GalleryController {
    private GalleryService galleryService;
    private GalleryConverter galleryConverter;

    public GalleryController(GalleryService galleryService, GalleryConverter galleryConverter) {
        this.galleryService = galleryService;
        this.galleryConverter = galleryConverter;
    }

    @GetMapping
    public List<GalleryListEntryResponse> getAllGalleries() {
        Iterable<GalleryEntity> entities = galleryService.getAllGalleries();
        return galleryConverter.convert(entities);
    }

    @PostMapping
    public CreateGalleryResponse createGallery(@Valid @RequestBody CreateGalleryRequest request) {
        GalleryEntity response = galleryService.createGallery(request);
        return CreateGalleryResponse.builder()
                .withGalleryId(response.getId())
                .build();
    }

    @GetMapping("/{galleryId}")
    public GetGalleryResponse getImagesInGallery(@PathVariable("galleryId") String galleryId) {
        GalleryEntity gallery = galleryService.findGallery(galleryId);
        return galleryConverter.convertToGalleryResponse(gallery);
    }

}
