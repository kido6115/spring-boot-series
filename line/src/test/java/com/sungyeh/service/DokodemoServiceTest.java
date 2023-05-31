package com.sungyeh.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * DokodemoServiceTest
 *
 * @author sungyeh
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
public class DokodemoServiceTest {
    @Resource
    private DokodemoService dokodemoService;

    @Test
    public void test() {
        dokodemoService.broadcast();
    }

}
