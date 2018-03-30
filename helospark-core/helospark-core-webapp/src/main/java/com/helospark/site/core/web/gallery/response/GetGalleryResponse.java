package com.helospark.site.core.web.gallery.response;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

public class GetGalleryResponse {
    private String id;
    private String title;
    private String description;
    private List<GalleryImage> images;

    @Generated("SparkTools")
    private GetGalleryResponse(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.images = builder.images;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<GalleryImage> getImages() {
        return images;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(GetGalleryResponse galleryImageResponse) {
        return new Builder(galleryImageResponse);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String id;
        private String title;
        private String description;
        private List<GalleryImage> images = Collections.emptyList();

        private Builder() {
        }

        private Builder(GetGalleryResponse galleryImageResponse) {
            this.id = galleryImageResponse.id;
            this.title = galleryImageResponse.title;
            this.description = galleryImageResponse.description;
            this.images = galleryImageResponse.images;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withImages(List<GalleryImage> images) {
            this.images = images;
            return this;
        }

        public GetGalleryResponse build() {
            return new GetGalleryResponse(this);
        }
    }
}
