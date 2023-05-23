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

    /**
     * 登入頁面
     *
     * @return String
     */
    @GetMapping("/oauth2/login")
    private String login() {
        return "oauth-login";
    }
}
