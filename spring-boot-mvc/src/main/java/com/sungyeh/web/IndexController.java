package com.sungyeh.web;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sungyeh.config.CaptchaConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 開放服務
 *
 * @author sungyeh
 */
@Controller
public class IndexController {


    @Resource
    private CaptchaConfig captchaConfig;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("site", captchaConfig.getSite());
        return "login";
    }

    @GetMapping("/acl/{user}")
    @ResponseBody
    public List<String> acl(@PathVariable("user") String user) {
        List<String> acl = new ArrayList<>();
        RequestMapping requestMapping = AuthenticationController.class.getAnnotation(RequestMapping.class);
        String main = requestMapping.value()[0];

        Class<?> clazz = AuthenticationController.class;
        for (var method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class) && !method.isAnnotationPresent(ResponseBody.class)) {
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                for (var value : getMapping.value()) {
                    acl.add(main + "/" + value);
                }
            }
        }
        return acl;
    }

    @PostMapping("/google/gsi")
    public String gsi(@RequestParam("credential") String credential, @RequestParam("g_csrf_token") String gToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("329193090490-5phkddntmnd815q9edcurgr4tc92csfs.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();
        try {
            GoogleIdToken idToken = verifier.verify(credential);
            System.out.println(idToken);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(userDetails, null,
//                        userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "index";
    }

}
