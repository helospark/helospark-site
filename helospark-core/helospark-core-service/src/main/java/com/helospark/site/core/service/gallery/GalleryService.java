package com.helospark.site.core.service.gallery;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.helospark.site.core.common.gallery.request.CreateGalleryRequest;
import com.helospark.site.core.service.common.UrlFriendlyIdFinder;
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
    private UrlFriendlyIdFinder urlFriendlyIdFinder;

    public GalleryService(GalleryEntityRepository galleryEntityRepository, GalleryImageEntityRepository galleryImageEntityRepository,
            ImageService imageService, UrlFriendlyIdFinder urlFriendlyIdFinder) {
        this.galleryEntityRepository = galleryEntityRepository;
        this.galleryImageEntityRepository = galleryImageEntityRepository;
        this.imageService = imageService;
        this.urlFriendlyIdFinder = urlFriendlyIdFinder;
    }

    public Iterable<GalleryEntity> getAllGalleries() {
        return galleryEntityRepository.findAll();
    }

    public GalleryEntity createGallery(CreateGalleryRequest request) {
        String id = urlFriendlyIdFinder.createId(galleryEntityRepository, request.getTitle());
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

    @Transactional
    //    @AuthorizedForAdmin // TODO
    public GalleryImageEntity saveImage(@Valid GalleryImageUploadRequest request) {
        MultipartFile multipartFile = request.getFile();
        GalleryEntity gallery = this.findGallery(request.getGalleryId());
        String imageId = urlFriendlyIdFinder.createId(galleryImageEntityRepository, request.getTitle());

        ImageEntity imageEntity = imageService.save(imageId, multipartFile);

        GalleryImageEntity entity = new GalleryImageEntity();
        entity.setId(imageId);
        entity.setImageEntity(imageEntity);
        entity.setTitle(request.getTitle());
        entity.setGallery(gallery);
        entity.setDescription(request.getDescription());

        return galleryImageEntityRepository.save(entity);
    }

}
