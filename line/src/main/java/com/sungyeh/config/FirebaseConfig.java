package com.sungyeh.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * FirebaseConfig
 *
 * @author sungyeh
 */
@Configuration
public class FirebaseConfig {
    /**
     * firebase設定
     */
    @Resource
    private FirebaseInfoConfig firebaseInfoConfig;


    /**
     * 註冊firebaseApp
     *
     * @return FirebaseApp
     * @throws IOException IOException
     */
    @Bean
    FirebaseApp firebaseApp() throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream(firebaseInfoConfig.getServiceAccount());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return FirebaseApp.initializeApp(options);

    }

}
