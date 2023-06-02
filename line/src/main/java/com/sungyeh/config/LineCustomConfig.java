package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * LineCustomConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "line.custom")
@Component
@Data
public class LineCustomConfig {

    /**
     * channelToken
     */
    private String channelToken;
}
