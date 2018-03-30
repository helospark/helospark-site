package com.helospark.site.core.service.gallery.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.context.annotation.Lazy;

import com.helospark.site.core.service.image.repository.ImageEntity;

@Entity(name = "gallery_image")
public class GalleryImageEntity {
    @Id
    private String id;
    private String title;
    private String description;
    @ManyToOne
    private ImageEntity imageEntity;
    @Lazy
    @ManyToOne
    private GalleryEntity gallery;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    public GalleryEntity getGallery() {
        return gallery;
    }

    public void setGallery(GalleryEntity gallery) {
        this.gallery = gallery;
    }

}
