package com.helospark.site.core.service.image.dao.repository;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "local_image_filesystem_mapping")
public class LocalFileSystemMapping {
    @Id
    // One to one
    // Foreign key imageEntity.id
    private String id;

    private String filePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
