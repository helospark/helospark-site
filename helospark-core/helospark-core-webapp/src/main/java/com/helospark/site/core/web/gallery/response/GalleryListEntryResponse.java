package com.helospark.site.core.web.gallery.response;

import java.util.Objects;

import javax.annotation.Generated;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GalleryListEntryResponse.Builder.class)
public class GalleryListEntryResponse {
    private String id;
    private String thumbnailUrl;
    private String title;

    @Generated("SparkTools")
    private GalleryListEntryResponse(Builder builder) {
        this.id = builder.id;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.title = builder.title;
    }

    public String getTitle() {
        return title;
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
    public static final class Builder {
        private String id;
        private String thumbnailUrl;
        private String title;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public GalleryListEntryResponse build() {
            return new GalleryListEntryResponse(this);
        }
    }

}
