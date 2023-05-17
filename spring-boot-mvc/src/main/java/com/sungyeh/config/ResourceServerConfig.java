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

    @Value("${oauth.authorization-server}")
    private String authorizationServer;

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
