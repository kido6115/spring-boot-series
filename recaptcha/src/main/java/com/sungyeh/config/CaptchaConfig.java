package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Google recaptcha 靜態資源
 *
 * @author sungyeh
 */
@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
@Data
public class CaptchaConfig {

    /**
     * site key
     */
    private String site;

    /**
     * secret key
     */
    private String secret;
}
