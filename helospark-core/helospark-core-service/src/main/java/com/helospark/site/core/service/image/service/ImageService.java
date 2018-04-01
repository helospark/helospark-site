package com.helospark.site.core.service.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.helospark.site.core.common.image.form.GetImageForm;
import com.helospark.site.core.service.common.UrlFriendlyIdFinder;
import com.helospark.site.core.service.image.ForbiddenToResizeImageException;
import com.helospark.site.core.service.image.dao.LocalFileSystemBasedImageDao;
import com.helospark.site.core.service.image.repository.ImageEntity;
import com.helospark.site.core.service.image.repository.ImageRepository;

@Service
public class ImageService {
    private LocalFileSystemBasedImageDao imageDao;
    private ImageRepository imageRepository;
    private ImageResizerService imageResizerService;
    private ImageResizerHashCalculator hashCalculator;
    private UrlFriendlyIdFinder urlFriendlyIdFinder;

    public ImageService(LocalFileSystemBasedImageDao imageDao, ImageRepository imageRepository, ImageResizerService imageResizerService,
            ImageResizerHashCalculator hashCalculator) {
        this.imageDao = imageDao;
        this.imageRepository = imageRepository;
        this.imageResizerService = imageResizerService;
        this.hashCalculator = hashCalculator;
    }

    public byte[] getImage(GetImageForm form) {
        assertHashIsValid(form);
        BufferedImage image = imageDao.getFileContent(form.getImageId());
        BufferedImage resizedImage = imageResizerService.resize(image, form.getWidth(), form.getHeight());
        return convertToJpg(resizedImage);
    }

    public ImageEntity save(String id, MultipartFile multipartFile) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
            BufferedImage image = ImageIO.read(bais);

            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setId(id);
            imageEntity.setWidth(image.getWidth());
            imageEntity.setHeight(image.getHeight());

            ImageEntity createdImageEntity = imageRepository.save(imageEntity);
            imageDao.saveImageFor(createdImageEntity, multipartFile.getBytes());

            return createdImageEntity;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to save image", e);
        }
    }

    public byte[] getOriginalImage(String imageId) {
        BufferedImage image = imageDao.getFileContent(imageId);
        return convertToJpg(image);
    }

    public void saveImage() {

    }

    private void assertHashIsValid(@Valid GetImageForm form) {
        String hash = hashCalculator.getHash(form.getImageId(), form.getWidth(), form.getHeight());
        if (!hash.equals(form.getHash())) {
            throw new ForbiddenToResizeImageException();
        }
    }

    private byte[] convertToJpg(BufferedImage resizedImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Unable to resize jpg");
        }
    }

}
