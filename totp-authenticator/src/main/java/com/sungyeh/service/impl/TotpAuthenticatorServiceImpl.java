package com.sungyeh.service.impl;

import com.sungyeh.service.TotpAuthenticatorService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

/**
 * TotpAuthenticatorService
 *
 * @author sungyeh
 */
@Service
public class TotpAuthenticatorServiceImpl implements TotpAuthenticatorService {
    /**
     * {@inheritDoc}
     */
    public String createUrl(String secret, String userId) {
        String otpType = "totp";
        String issuer = "sungyeh-tech-note";
        String account = issuer + ":" + userId;
        String googleAuthenticatorKeyUriFormat = "otpauth://%s/%s?secret=%s&issuer=%s";
        return String.format(googleAuthenticatorKeyUriFormat, otpType, account, secret, issuer);
    }

    /**
     * <p>createKey.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String createKey() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        return key.getKey();
    }

    /**
     * {@inheritDoc}
     */
    public boolean valid(int code, String secret) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        return googleAuthenticator.authorize(secret, code);
    }
}
