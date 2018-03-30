package com.helospark.site.core.web.gallery.response;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = GalleryListEntryResponse.Builder.class)
public class GalleryListEntryResponse {
    private String id;
    private String thumbnailUrl;

    @Generated("SparkTools")
    private GalleryListEntryResponse(Builder builder) {
        this.id = builder.id;
        this.thumbnailUrl = builder.thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof GalleryListEntryResponse)) {
            return false;
        }
        GalleryListEntryResponse castOther = (GalleryListEntryResponse) other;
        return Objects.equals(id, castOther.id) && Objects.equals(thumbnailUrl, castOther.thumbnailUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thumbnailUrl);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static Builder builderFrom(GalleryListEntryResponse galleryListEntryResponse) {
        return new Builder(galleryListEntryResponse);
    }

    @JsonPOJOBuilder
    @Generated("SparkTools")
    public static final class Builder {
        private String id;
        private String thumbnailUrl;

        private Builder() {
        }

        private Builder(GalleryListEntryResponse galleryListEntryResponse) {
            this.id = galleryListEntryResponse.id;
            this.thumbnailUrl = galleryListEntryResponse.thumbnailUrl;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public GalleryListEntryResponse build() {
            return new GalleryListEntryResponse(this);
        }
    }

}
