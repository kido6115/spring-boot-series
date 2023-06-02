package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger靜態資源
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "oas")
@Data
@Component
public class OasConfig {

    /**
     * Swagger 網站URL
     */
    private String url;
    /**
     * Swagger 網站擁有者
     */
    private String owner;
    /**
     * Swagger 網站擁有者信箱
     */
    private String mail;
}
