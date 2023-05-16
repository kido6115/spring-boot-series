package com.sungyeh.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController
 *
 * @author sungyeh
 */
@RestController
@RequestMapping("/rest")
public class RestDemoController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("list")
    public List<String> list() {
        return List.of("a", "b", "c");
    }


}
