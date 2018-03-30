package com.helospark.site.core.common.gallery.request;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CreateGalleryRequest {
    @NotNull
    @Size(min = 4)
    private String title;
    @NotEmpty
    private String description;

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

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof CreateGalleryRequest)) {
            return false;
        }
        CreateGalleryRequest castOther = (CreateGalleryRequest) other;
        return Objects.equals(title, castOther.title) && Objects.equals(description, castOther.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }

    //TODO: Generate after https://bugs.eclipse.org/bugs/show_bug.cgi?id=521995 is resolved
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
