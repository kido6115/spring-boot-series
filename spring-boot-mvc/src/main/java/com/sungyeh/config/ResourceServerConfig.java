package com.sungyeh.config;

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

    @Bean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/rest/**").authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt().decoder(NimbusJwtDecoder.withJwkSetUri("http://127.0.0.1:8081" + "/oauth2/jwks").build())
                );
        return http.build();
    }
}
