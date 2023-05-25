package com.sungyeh.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * FirebaseConfig
 *
 * @author sungyeh
 */
@Configuration
public class FirebaseConfig {
    @Resource
    private FirebaseInfoConfig firebaseInfoConfig;


    @Bean
    FirebaseApp firebaseApp() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(firebaseInfoConfig.getServiceAccountUrl(), String.class);
        InputStream inputStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()).getBytes());


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        return FirebaseApp.initializeApp(options);

    }

}
