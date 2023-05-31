package com.sungyeh.web;

import com.sungyeh.service.DokodemoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BroadcastController
 *
 * @author sungyeh
 */
@RestController
public class BroadcastController {

    /**
     * DokodemoService
     */
    @Resource
    private DokodemoService dokodemoService;

    /**
     * 推撥
     */
    @GetMapping("/broadcast")
    public void broadcast() {
        dokodemoService.broadcast();
    }
}

