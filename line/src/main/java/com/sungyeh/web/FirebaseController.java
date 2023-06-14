package com.sungyeh.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sungyeh.bean.firebase.LocationDocument;
import com.sungyeh.service.FirebaseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping("add-location")
    public String addLocation(@RequestParam("ip") String ip,
                              @RequestParam("lat") String lat,
                              @RequestParam("lng") String lng,
                              @RequestParam("city") String city) throws JsonProcessingException {
        return firebaseService.addLocation(LocationDocument.builder(ip, lat, lng, city));
    }

    @GetMapping("group-location")
    public Map<String, Integer> getGroupLocation() {
        return firebaseService.groupLocation();
    }
}
