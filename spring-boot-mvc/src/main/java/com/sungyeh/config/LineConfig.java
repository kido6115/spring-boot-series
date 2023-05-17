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

    private String id;

    private String secret;

    private String redirect;
}
