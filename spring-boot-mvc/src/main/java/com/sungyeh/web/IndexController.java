package com.sungyeh.web;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sungyeh.bean.GoogleUserInfo;
import com.sungyeh.bean.LineAuthorizationCodeResponse;
import com.sungyeh.bean.LineUserinfo;
import com.sungyeh.bean.OauthAuthorizationCodeResponse;
import com.sungyeh.config.CaptchaConfig;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
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

    @Value("${google.oauth.id}")
    private String clientId;

    @Value("${google.oauth.secret}")
    private String clientSecret;

    @Value("${google.oauth.redirect}")
    private String redirect;

    @Value("${line.oauth.id}")
    private String lineClientId;

    @Value("${line.oauth.secret}")
    private String lineClientSecret;

    @Value("${line.oauth.redirect}")
    private String lineRedirect;

    @Value("${oauth.id}")
    private String oauthClientId;

    @Value("${oauth.secret}")
    private String oauthClientSecret;

    @Value("${oauth.redirect}")
    private String oauthRedirect;

    @Value("${oauth.authorization-endpoint}")
    private String endpoint;

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

    @GetMapping("/google/openid")
    public String googleOpenid(@RequestParam("code") String code, Model model) throws IOException {

        GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), new GsonFactory(),
                clientId,
                clientSecret,
                code, redirect)
                .execute();

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("Authorization", "Bearer " + response.getAccessToken());
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map);
        ResponseEntity<GoogleUserInfo> result = restTemplate.exchange("https://www.googleapis.com/oauth2/v1/userinfo", HttpMethod.GET, entity, GoogleUserInfo.class);
        model.addAttribute("token", response.getAccessToken());
        model.addAttribute("result", result.getBody());

        return "google-openid";
    }

    @GetMapping("/line/openid")
    public String lineOpenid(@RequestParam("code") String code, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", lineRedirect);
        map.add("client_id", lineClientId);
        map.add("client_secret", lineClientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<LineAuthorizationCodeResponse> authorization = restTemplate.exchange("https://api.line.me/oauth2/v2.1/token", HttpMethod.POST, entity, LineAuthorizationCodeResponse.class);

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> userMap = new LinkedMultiValueMap<>();
        map.add("id_token", authorization.getBody().getIdToken());
        map.add("client_id", lineClientId);

        HttpEntity<MultiValueMap<String, String>> userEntity = new HttpEntity<>(userMap, userHeaders);
        ResponseEntity<LineUserinfo> result = restTemplate.exchange("https://api.line.me/oauth2/v2.1/verify", HttpMethod.POST, entity, LineUserinfo.class);


        model.addAttribute("authorization", authorization.getBody());
        model.addAttribute("result", result.getBody());
        return "line-openid";
    }

    @GetMapping("/oauth/openid")
    public String oauthOpenid(@RequestParam("code") String code, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((oauthClientId + ":" + oauthClientSecret).getBytes()));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", oauthRedirect);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<OauthAuthorizationCodeResponse> authorization = restTemplate.exchange(endpoint, HttpMethod.POST, entity, OauthAuthorizationCodeResponse.class);
        model.addAttribute("authorization", authorization.getBody());
        return "oauth-openid";
    }

}
