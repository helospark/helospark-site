package com.helospark.site.core.web.gallery.response;

import javax.annotation.Generated;

public class GalleryListEntry {
    private String title;
    private String thumbnailUrl;
    private String galleryId;

    @Generated("SparkTools")
    private GalleryListEntry(Builder builder) {
        this.title = builder.title;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.galleryId = builder.galleryId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getGalleryId() {
        return galleryId;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(GalleryListEntry galleryListResponse) {
        return new Builder(galleryListResponse);
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String title;
        private String thumbnailUrl;
        private String galleryId;

        private Builder() {
        }

        private Builder(GalleryListEntry galleryListResponse) {
            this.title = galleryListResponse.title;
            this.thumbnailUrl = galleryListResponse.thumbnailUrl;
            this.galleryId = galleryListResponse.galleryId;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder withGalleryId(String galleryId) {
            this.galleryId = galleryId;
            return this;
        }

        public GalleryListEntry build() {
            return new GalleryListEntry(this);
        }
    }

}
