package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OasConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "oas")
@Data
@Component
public class OasConfig {

    private String url;

    private String owner;

    private String mail;
}
