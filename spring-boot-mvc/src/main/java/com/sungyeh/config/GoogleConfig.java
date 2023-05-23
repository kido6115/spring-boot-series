package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Google oauth 靜態資源
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "google.oauth")
@Data
@Component
public class GoogleConfig {
    /**
     * client id
     */
    private String id;
    /**
     * client secret
     */
    private String secret;
    /**
     * 重導向URL
     */
    private String redirect;
}
