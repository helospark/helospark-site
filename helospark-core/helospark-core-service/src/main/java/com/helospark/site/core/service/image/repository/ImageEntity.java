package com.helospark.site.core.service.image.repository;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "image")
public class ImageEntity {
    @Id
    private String id;
    private int width;
    private int height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
