package com.sungyeh.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 自訂義驗證資訊
 *
 * @author sungyeh
 */
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class RecaptchaAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * token
     */
    @Getter
    private String token;

    @Getter
    private String lat;

    @Getter
    private String lng;


    /**
     * 建構子
     *
     * @param request HttpServletRequest
     */
    public RecaptchaAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.token = request.getParameter("g-recaptcha-response");
        this.lat = request.getParameter("lat");
        this.lng = request.getParameter("lng");
    }
}
