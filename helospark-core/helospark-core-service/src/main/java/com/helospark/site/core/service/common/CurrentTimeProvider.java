package com.helospark.site.core.service.common;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public class CurrentTimeProvider {

    public ZonedDateTime currentZonedDateTime() {
        return ZonedDateTime.now();
    }
}
