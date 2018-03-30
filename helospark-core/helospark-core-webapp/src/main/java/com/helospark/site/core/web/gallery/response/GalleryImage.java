package com.helospark.site.core.web.gallery.response;

import javax.annotation.Generated;

public class GalleryImage {
    private String title;
    private String description;
    private String thumbnailUrl;
    private String largeUrl;
    private String originalUrl;

    @Generated("SparkTools")
    private GalleryImage(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.largeUrl = builder.largeUrl;
        this.originalUrl = builder.originalUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(GalleryImage galleryImages) {
        return new Builder(galleryImages);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String title;
        private String description;
        private String thumbnailUrl;
        private String largeUrl;
        private String originalUrl;

        private Builder() {
        }

        private Builder(GalleryImage galleryImages) {
            this.title = galleryImages.title;
            this.description = galleryImages.description;
            this.thumbnailUrl = galleryImages.thumbnailUrl;
            this.largeUrl = galleryImages.largeUrl;
            this.originalUrl = galleryImages.originalUrl;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder withLargeUrl(String largeUrl) {
            this.largeUrl = largeUrl;
            return this;
        }

        public Builder withOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
            return this;
        }

        public GalleryImage build() {
            return new GalleryImage(this);
        }
    }
}
