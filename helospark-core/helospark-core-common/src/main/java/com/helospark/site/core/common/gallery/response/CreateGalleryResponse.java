package com.helospark.site.core.common.gallery.response;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = CreateGalleryResponse.Builder.class)
public class CreateGalleryResponse {
    private String galleryId;

    @Generated("SparkTools")
    private CreateGalleryResponse(Builder builder) {
        this.galleryId = builder.galleryId;
    }

    public String getGalleryId() {
        return galleryId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof CreateGalleryResponse)) {
            return false;
        }
        CreateGalleryResponse castOther = (CreateGalleryResponse) other;
        return Objects.equals(galleryId, castOther.galleryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(galleryId);
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
    public static Builder builderFrom(CreateGalleryResponse createGalleryResponse) {
        return new Builder(createGalleryResponse);
    }

    @JsonPOJOBuilder
    @Generated("SparkTools")
    public static final class Builder {
        private String galleryId;

        private Builder() {
        }

        private Builder(CreateGalleryResponse createGalleryResponse) {
            this.galleryId = createGalleryResponse.galleryId;
        }

        public Builder withGalleryId(String galleryId) {
            this.galleryId = galleryId;
            return this;
        }

        public CreateGalleryResponse build() {
            return new CreateGalleryResponse(this);
        }
    }

}
