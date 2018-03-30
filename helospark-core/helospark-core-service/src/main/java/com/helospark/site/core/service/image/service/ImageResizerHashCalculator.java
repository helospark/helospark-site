package com.helospark.site.core.service.image.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageResizerHashCalculator {
    private String secret;

    public ImageResizerHashCalculator(@Value("security.image.development_image_secret_key") String secret) {
        this.secret = secret;
    }

    public String getHash(String fileId, int width, int height) {
        String beforeHash = secret + "," + fileId + "," + width + "," + height;
        return hash(beforeHash);
    }

    private String hash(String beforeHash) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return bytesToHex(digest.digest(beforeHash.getBytes(UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Unable to hash", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
