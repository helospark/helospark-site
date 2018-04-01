package com.helospark.site.core.web.image;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.http.MediaType.IMAGE_JPEG;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helospark.site.core.common.image.form.GetImageForm;
import com.helospark.site.core.service.image.service.ImageService;

@RestController
@RequestMapping("/images")
public class GetImageController {
    private int imageCacheTime;
    private ImageService imageService;

    public GetImageController(ImageService imageService, @Value("${image.cache.time}") int imageCacheTime) {
        this.imageService = imageService;
        this.imageCacheTime = imageCacheTime;
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getImage(@Valid GetImageForm form) {
        byte[] data = imageService.getImage(form);
        return createResponse(data);
    }

    @GetMapping("/{imageId}/original")
    public ResponseEntity<byte[]> getOriginalImage(@PathVariable String imageId) {
        byte[] data = imageService.getOriginalImage(imageId);
        return createResponse(data);
    }

    private ResponseEntity<byte[]> createResponse(byte[] data) {
        return ResponseEntity.ok()
                .contentType(IMAGE_JPEG)
                .varyBy("Cache-Control")
                .cacheControl(CacheControl.maxAge(imageCacheTime, SECONDS).cachePublic())
                .body(data);
    }

}
