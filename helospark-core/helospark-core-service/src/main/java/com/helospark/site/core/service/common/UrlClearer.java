package com.helospark.site.core.service.common;

import org.springframework.stereotype.Service;

@Service
public class UrlClearer {

    public String clearString(String data) {
        return data.replaceAll("\\s", "-")
                .replaceAll("[^a-zA-Z\\-]", "");
    }

}
