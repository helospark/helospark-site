package com.helospark.site.core.service.gallery.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.context.annotation.Lazy;

import com.helospark.site.core.service.image.repository.ImageEntity;

@Entity(name = "gallery")
public class GalleryEntity {
    @Id
    private String id;
    private String title;
    @Lazy
    @ManyToOne
    private ImageEntity thumbnail;
    @Lazy
    private String description;
    @Lazy
    @OneToMany(mappedBy = "gallery")
    private List<GalleryImageEntity> images;

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

    public Optional<ImageEntity> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    public void setThumbnail(ImageEntity thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<GalleryImageEntity> getImages() {
        return images;
    }

    public void setImages(List<GalleryImageEntity> images) {
        this.images = images;
    }

}
