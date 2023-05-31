package com.sungyeh.config;

import com.sungyeh.security.CsrfRequestMatcher;
import com.sungyeh.security.CustomProvider;
import com.sungyeh.security.RecaptchaAuthenticationDetailsSource;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
@Order(1)
public class SecurityConfig {

    /**
     * CustomProvider
     * 自訂驗證
     */
    @Resource
    private CustomProvider provider;

    /**
     * security filter chain
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws java.lang.Exception java.lang.Exception
     */
    @Bean("backendFilterChain")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfRequestMatcher csrfRequestMatcher = new CsrfRequestMatcher();
        csrfRequestMatcher.addExcludeUrl("/google/gsi");
        csrfRequestMatcher.addExcludePath("/text-ccs/**");
        csrfRequestMatcher.addExcludePath("/dialogflow/**");
        csrfRequestMatcher.addExcludePath("/h2/**");

        http.authorizeHttpRequests(a -> a.requestMatchers("/auth/**").authenticated()
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
                .logoutSuccessUrl("/index")
                .and()
                .exceptionHandling()
                .and()
                .authenticationProvider(provider)
                .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher)
                .and()
                .headers().frameOptions().sameOrigin()
                .xssProtection().headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK);

        return http.build();
    }

    /**
     * password encoder
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
