package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * LineConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "line.oauth")
@Data
@Component
public class LineConfig {
    /**
     * channel id
     */
    private String id;
    /**
     * channel secret
     */
    private String secret;
    /**
     * 重導向URL
     */
    private String redirect;
}
