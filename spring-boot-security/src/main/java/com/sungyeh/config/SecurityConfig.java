package com.sungyeh.config;

import com.sungyeh.security.CustomProvider;
import com.sungyeh.security.RecaptchaAuthenticationDetailsSource;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

/**
 * SecurityConfig
 *
 * @author sungyeh
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private CustomProvider provider;

    @Bean("backendFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(a -> a.requestMatchers("/auth/**").authenticated()
                        .requestMatchers("/h2/**").permitAll()
                        .anyRequest().permitAll())
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login/process").permitAll()
                .defaultSuccessUrl("/index")
                .failureUrl("/login")
                .authenticationDetailsSource(new RecaptchaAuthenticationDetailsSource())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling()
                .and()
                .authenticationProvider(provider)
                .csrf()
                .and()
                .headers().frameOptions().sameOrigin()
                .xssProtection().headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
