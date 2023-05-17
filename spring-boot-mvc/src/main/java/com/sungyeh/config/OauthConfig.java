package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OauthConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "oauth")
@Data
@Component
public class OauthConfig {
    private String id;

    private String secret;

    private String redirect;

    private String authorizationEndpoint;

    private String authorizationServer;
}
