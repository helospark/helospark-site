package com.helospark.site.core.service.common;

import java.time.format.DateTimeFormatter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.helospark.site.core.service.gallery.CannotFindValidId;

@Component
public class UrlFriendlyIdFinder {
    private UrlClearer urlClearer;
    private CurrentTimeProvider currentTimeProvider;

    public UrlFriendlyIdFinder(UrlClearer urlClearer, CurrentTimeProvider currentTimeProvider) {
        this.urlClearer = urlClearer;
        this.currentTimeProvider = currentTimeProvider;
    }

    public String createId(CrudRepository<?, String> repository, String title) {
        String clearedTitle = urlClearer.clearString(title).toLowerCase();
        if (clearedTitle.length() < 2) {
            clearedTitle = clearedTitle + currentTimeProvider.currentLocalDateTime().format(DateTimeFormatter.ISO_DATE);
        }
        for (int i = 0; i < 10; ++i) {
            String tmpTitle = clearedTitle;
            if (i != 0) {
                tmpTitle += "-" + i;
            }
            if (!repository.findById(tmpTitle).isPresent()) {
                return tmpTitle;
            }
        }
        throw new CannotFindValidId("Cannot find valid ID for the article");

    }
}
