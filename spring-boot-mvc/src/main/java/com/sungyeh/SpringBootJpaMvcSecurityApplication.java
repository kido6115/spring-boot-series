package com.sungyeh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>SpringBootJpaMvcSecurityApplication class.</p>
 *
 * @author sungyeh
 */
@SpringBootApplication
@EnableAsync
public class SpringBootJpaMvcSecurityApplication {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaMvcSecurityApplication.class, args);
    }

}
