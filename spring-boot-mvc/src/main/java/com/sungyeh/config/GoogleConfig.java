package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * GoogleConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "google.oauth")
@Data
@Component
public class GoogleConfig {

    private String id;

    private String secret;

    private String redirect;
}
