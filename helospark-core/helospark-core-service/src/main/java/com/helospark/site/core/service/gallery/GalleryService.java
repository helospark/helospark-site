package com.helospark.site.core.service.gallery;

import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.helospark.site.core.common.gallery.request.CreateGalleryRequest;
import com.helospark.site.core.service.common.AuthorizedForAdmin;
import com.helospark.site.core.service.common.CurrentTimeProvider;
import com.helospark.site.core.service.common.UrlClearer;
import com.helospark.site.core.service.gallery.domain.GalleryEntity;
import com.helospark.site.core.service.gallery.domain.GalleryImageEntity;
import com.helospark.site.core.service.gallery.repository.GalleryEntityRepository;
import com.helospark.site.core.service.gallery.repository.GalleryImageEntityRepository;
import com.helospark.site.core.service.image.repository.ImageEntity;
import com.helospark.site.core.service.image.service.ImageService;
import com.helospark.site.core.web.gallery.request.GalleryImageUploadRequest;

@Service
public class GalleryService {
    private GalleryEntityRepository galleryEntityRepository;
    private GalleryImageEntityRepository galleryImageEntityRepository;
    private ImageService imageService;
    private UrlClearer urlClearer;
    private CurrentTimeProvider currentTimeProvider;

    public GalleryService(GalleryEntityRepository galleryEntityRepository, GalleryImageEntityRepository galleryImageEntityRepository,
            ImageService imageService, UrlClearer urlClearer, CurrentTimeProvider currentTimeProvider) {
        this.galleryEntityRepository = galleryEntityRepository;
        this.galleryImageEntityRepository = galleryImageEntityRepository;
        this.imageService = imageService;
        this.urlClearer = urlClearer;
        this.currentTimeProvider = currentTimeProvider;
    }

    public Iterable<GalleryEntity> getAllGalleries() {
        return galleryEntityRepository.findAll();
    }

    public GalleryEntity createGallery(CreateGalleryRequest request) {
        String id = findUniqueId(request.getTitle());
        GalleryEntity entity = convertToGalleryEntity(request, id);
        return galleryEntityRepository.save(entity);
    }

    public GalleryEntity findGallery(String galleryId) {
        return galleryEntityRepository.findById(galleryId)
                .orElseThrow(() -> new GalleryNotFoundException("Gallery not found with id " + galleryId));
    }

    private GalleryEntity convertToGalleryEntity(CreateGalleryRequest request, String id) {
        GalleryEntity entity = new GalleryEntity();
        entity.setId(id);
        entity.setDescription(request.getDescription());
        entity.setTitle(request.getTitle());
        return entity;
    }

    private String findUniqueId(String title) {
        String clearedTitle = urlClearer.clearString(title).toLowerCase();
        if (clearedTitle.length() < 2) {
            clearedTitle = clearedTitle + currentTimeProvider.currentLocalDateTime().format(DateTimeFormatter.ISO_DATE);
        }
        for (int i = 0; i < 10; ++i) {
            String tmpTitle = clearedTitle;
            if (i != 0) {
                tmpTitle += "-" + i;
            }
            if (!galleryEntityRepository.findById(tmpTitle).isPresent()) {
                return tmpTitle;
            }
        }
        throw new CannotFindValidId("Cannot find valid ID for the article");
    }

    @Transactional
    @AuthorizedForAdmin
    public GalleryImageEntity saveImage(@Valid GalleryImageUploadRequest request) {
        MultipartFile multipartFile = request.getFile();
        GalleryEntity gallery = this.findGallery(request.getGalleryId());

        ImageEntity imageEntity = imageService.save(multipartFile);

        GalleryImageEntity entity = new GalleryImageEntity();
        entity.setId(request.getTitle()); // TODO: generate id
        entity.setImageEntity(imageEntity);
        entity.setTitle(request.getTitle());
        entity.setGallery(gallery);
        entity.setDescription(request.getDescription());

        return galleryImageEntityRepository.save(entity);
    }

}
