package com.sungyeh.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger標的設定
 *
 * @author sungyeh
 */
@Configuration
public class SpringdocConfig {

    /**
     * API 群組設定
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi sungyehApi() {
        return GroupedOpenApi.builder()
                .group("sungyeh-api")
                .packagesToScan("com.sungyeh.web.rest")
                .pathsToMatch("/rest/**")
                .build();
    }

}
