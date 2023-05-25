package com.sungyeh.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * CwbServiceTest
 *
 * @author sungyeh
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CwbServiceTest {
    @Resource
    private CwbService cwbService;

    @Test
    public void testWeatherForecast() {
        cwbService.weatherForecast();
    }
}
