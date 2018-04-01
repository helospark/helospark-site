package com.helospark.site.core.web.gallery.response;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GalleryImage.Builder.class)
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

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof GalleryImage)) {
            return false;
        }
        GalleryImage castOther = (GalleryImage) other;
        return Objects.equals(title, castOther.title) && Objects.equals(description, castOther.description)
                && Objects.equals(thumbnailUrl, castOther.thumbnailUrl) && Objects.equals(largeUrl, castOther.largeUrl)
                && Objects.equals(originalUrl, castOther.originalUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, thumbnailUrl, largeUrl, originalUrl);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @JsonPOJOBuilder
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
