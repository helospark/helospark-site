package com.helospark.site.core.service.image.service.domain;

import org.springframework.http.MediaType;

class GetImageServiceResponse {
    private byte[] data;
    private MediaType mediaType;

    public GetImageServiceResponse(byte[] data, MediaType mediaType) {
        this.data = data;
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

}