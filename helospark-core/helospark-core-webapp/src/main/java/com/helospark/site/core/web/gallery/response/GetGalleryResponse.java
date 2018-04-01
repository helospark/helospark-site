package com.helospark.site.core.web.gallery.response;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetGalleryResponse.Builder.class)
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

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof GetGalleryResponse)) {
            return false;
        }
        GetGalleryResponse castOther = (GetGalleryResponse) other;
        return Objects.equals(id, castOther.id) && Objects.equals(title, castOther.title) && Objects.equals(description, castOther.description)
                && Objects.equals(images, castOther.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, images);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
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
