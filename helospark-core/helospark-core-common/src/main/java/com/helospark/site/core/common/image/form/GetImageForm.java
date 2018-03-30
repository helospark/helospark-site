package com.helospark.site.core.common.image.form;

public class GetImageForm {
    private String imageId;
    private int width;
    private int height;
    private String hash;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String fileName) {
        this.imageId = fileName;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
