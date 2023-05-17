package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SystemConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "system")
@Data
@Component
public class SystemConfig {

    private String url;
}
