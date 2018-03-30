package com.helospark.site.core.service.image.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.springframework.stereotype.Service;

@Service
public class ImageResizerService {

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();
        BufferedImage result = new BufferedImage(newW, newH, img.getType());
        Graphics2D graphics = result.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(img, 0, 0, newW, newH, 0, 0, originalWidth, originalHeight, null);
        graphics.dispose();
        return result;
    }
}
