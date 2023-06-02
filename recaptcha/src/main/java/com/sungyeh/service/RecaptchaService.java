package com.sungyeh.service;

import org.springframework.web.client.RestOperations;

/**
 * RecaptchaService
 *
 * @author sungyeh
 */
public interface RecaptchaService {

    /**
     * 驗證 recaptcha
     *
     * @param token recaptcha token
     * @return 驗證是否成功
     */
    boolean verify(String token);

    /**
     * 驗證 recaptcha
     *
     * @param restOperations restOperations
     * @param token          reCAPTCHA token
     * @return 驗證是否成功
     */
    boolean verify(RestOperations restOperations, String token);
}
