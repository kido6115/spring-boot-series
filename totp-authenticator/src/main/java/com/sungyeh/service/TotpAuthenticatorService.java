package com.sungyeh.service;

/**
 * TotpAuthenticatorService
 *
 * @author sungyeh
 */
public interface TotpAuthenticatorService {
    String createUrl(String secret, String userId);

    String createKey();

    boolean valid(int code, String secret);
}
