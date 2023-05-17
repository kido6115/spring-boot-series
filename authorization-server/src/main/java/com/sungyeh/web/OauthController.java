package com.sungyeh.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * OauthController
 *
 * @author sungyeh
 */
@Controller
public class OauthController {

    @GetMapping("/oauth2/login")
    private String login() {
        return "oauth-login";
    }
}
