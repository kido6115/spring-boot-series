package com.sungyeh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ResourceServerConfig
 *
 * @author sungyeh
 */
@Configuration
@EnableWebSecurity
public class ResourceServerConfig {
    /**
     * 認證伺服器
     */
    @Value("${oauth.authorization-server}")
    private String authorizationServer;

    /**
     * 資源伺服器filter chain
     *
     * @param http a {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} object
     * @return a {@link org.springframework.security.web.SecurityFilterChain} object
     * @throws java.lang.Exception if any.
     */
    @Bean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/rest/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/rest/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt().decoder(NimbusJwtDecoder.withJwkSetUri(authorizationServer + "/oauth2/jwks").build())
                );
        return http.build();
    }
}
