package com.helospark.site.core.web.image;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
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
    private ImageService imageService;

    public GetImageController(ImageService imageService) {
        this.imageService = imageService;
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", IMAGE_JPEG_VALUE);
        return new ResponseEntity<byte[]>(data, headers, OK);
    }

}
