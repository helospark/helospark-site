package com.helospark.site.core.common.image.form;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class SaveImageForm {
    private MultipartFile file;
    @NotEmpty
    private String fileName;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
