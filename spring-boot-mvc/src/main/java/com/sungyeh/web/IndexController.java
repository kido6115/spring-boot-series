package com.sungyeh.web;

import com.sungyeh.config.CaptchaConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

}
