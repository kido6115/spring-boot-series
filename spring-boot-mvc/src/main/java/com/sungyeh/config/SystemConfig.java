package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系統靜態資源
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "system")
@Data
@Component
public class SystemConfig {
    /**
     * 系統網址
     */
    private String url;
}
