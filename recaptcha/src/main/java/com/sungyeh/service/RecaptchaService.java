package com.sungyeh.service;

import org.springframework.web.client.RestOperations;

/**
 * RecaptchaService
 *
 * @author sungyeh
 */
public interface RecaptchaService {

    boolean verify(String token);

    boolean verify(RestOperations restOperations, String token);
}
