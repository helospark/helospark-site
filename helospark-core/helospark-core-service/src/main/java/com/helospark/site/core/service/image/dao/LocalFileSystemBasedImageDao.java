package com.helospark.site.core.service.image.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.helospark.site.core.service.common.CurrentTimeProvider;
import com.helospark.site.core.service.image.ImageNotFoundException;
import com.helospark.site.core.service.image.dao.repository.FileIdToFilePathRepository;
import com.helospark.site.core.service.image.dao.repository.LocalFileSystemMapping;
import com.helospark.site.core.service.image.repository.ImageEntity;

@Repository
public class LocalFileSystemBasedImageDao {
    private FileIdToFilePathRepository fileIdToFilePathRepository;
    private String filePath;
    private CurrentTimeProvider currentTimeProvider;

    public LocalFileSystemBasedImageDao(FileIdToFilePathRepository fileIdToFilePathRepository,
            CurrentTimeProvider currentTimeProvider,
            @Value("image.localfilesystem.basepath") String filePath) {
        this.fileIdToFilePathRepository = fileIdToFilePathRepository;
        this.currentTimeProvider = currentTimeProvider;
        this.filePath = filePath;
    }

    @PostConstruct
    public void init() {
        createDirectoryOrFail(filePath);
    }

    private File createDirectoryOrFail(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                throw new IllegalStateException("Cannot create image directory");
            }
        }
        if (!directory.isDirectory()) {
            throw new IllegalStateException("Image upload path is a NOT directory");
        }
        return directory;
    }

    public BufferedImage getFileContent(String fileId) {
        String filePath = getFilePath(fileId);
        return readFile(filePath);
    }

    public void saveImageFor(ImageEntity imageEntity, byte[] bytes) {
        try {
            File file = getFileName(imageEntity);
            writeFile(file, bytes);
            LocalFileSystemMapping asd = createFileSystemMapping(imageEntity, file);
            fileIdToFilePathRepository.save(asd);
        } catch (Exception e) {
            throw new RuntimeException("Unable to save filemapping", e);
        }
    }

    private LocalFileSystemMapping createFileSystemMapping(ImageEntity imageEntity, File file) {
        LocalFileSystemMapping fileSystemMapping = new LocalFileSystemMapping();
        fileSystemMapping.setId(imageEntity.getId());
        fileSystemMapping.setFilePath(file.getAbsolutePath());
        return fileSystemMapping;
    }

    private File getFileName(ImageEntity imageEntity) {
        LocalDateTime currentTime = currentTimeProvider.currentLocalDateTime();

        String realPath = filePath + "/" + currentTime.getYear() + "/" + currentTime.getMonthValue() + "/" + currentTime.getDayOfMonth();

        File directory = createDirectoryOrFail(realPath);
        return new File(directory, imageEntity.getId());
    }

    private void writeFile(File finalFileName, byte[] bytes) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(finalFileName);
        fos.write(bytes);
        fos.close();
    }

    private String getFilePath(String fileId) {
        return fileIdToFilePathRepository.findById(fileId)
                .map(data -> data.getFilePath())
                .orElseThrow(() -> new ImageNotFoundException("File id is not found"));
    }

    private BufferedImage readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalStateException("File is not found on the filesystem");
        }
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read image file", e);
        }
    }

}
