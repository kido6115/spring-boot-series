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

    @Resource
    private DokodemoService dokodemoService;

    @GetMapping("/broadcast")
    public void broadcast() {
        dokodemoService.broadcast();
    }
}

