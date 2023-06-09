package com.sungyeh.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sungyeh.bean.firebase.LocationDocument;
import com.sungyeh.service.FirebaseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * FirebaseController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping("/firebase")
public class FirebaseController {

    @Resource
    private FirebaseService firebaseService;

    /**
     * addLocation
     */
    @PostMapping("/add-location")
    public String addLocation(@RequestParam("ip") String ip,
                              @RequestParam("lat") String lat,
                              @RequestParam("lng") String lng) throws JsonProcessingException {
        return firebaseService.addLocation(LocationDocument.builder(ip, lat, lng));
    }
}
