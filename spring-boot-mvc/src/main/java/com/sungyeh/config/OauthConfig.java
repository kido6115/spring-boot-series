package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 認證伺服器靜態資源
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "oauth")
@Data
@Component
public class OauthConfig {
    /**
     * client id
     */
    private String id;
    /**
     * client secret
     */
    private String secret;
    /**
     * 重導向URL
     */
    private String redirect;
    /**
     * 認證端點
     */
    private String authorizationEndpoint;
    /**
     * 認證伺服器
     */
    private String authorizationServer;
}
