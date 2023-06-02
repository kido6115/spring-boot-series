package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Cloud vision 靜態資源
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "google.vision")
@Data
@Component
public class VisionConfig {

    /**
     * api key
     */
    private String key;
}
