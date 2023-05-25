package com.sungyeh.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * FirebaseConfig
 *
 * @author sungyeh
 */
@Configuration
public class FirebaseConfig {

    @Value("classpath:sungyeh-tech-note-firebase-adminsdk-aj6vo-e495412352.json")
    org.springframework.core.io.Resource resourceFile;

    @Bean
    FirebaseApp firebaseApp() throws IOException {

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(resourceFile.getInputStream()))
                .build();

        return FirebaseApp.initializeApp(options);

    }

}
