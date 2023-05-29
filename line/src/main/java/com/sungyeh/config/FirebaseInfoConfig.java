package com.sungyeh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FIrebaseInfoConfig
 *
 * @author sungyeh
 */
@ConfigurationProperties(prefix = "firebase")
@Data
@Component
public class FirebaseInfoConfig {

    private String key;

    private String serviceAccount;
}
