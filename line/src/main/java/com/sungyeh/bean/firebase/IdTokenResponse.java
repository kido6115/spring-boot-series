package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * IdTokenResponse
 *
 * @author sungyeh
 */
@Data
public class IdTokenResponse {
    /**
     * kind
     */
    private String kind;
    /**
     * idToken
     */
    private String idToken;
    /**
     * refreshToken
     */
    private String refreshToken;
    /**
     * expiresIn
     */
    private String expiresIn;
    /**
     * isNewUser
     */
    private String isNewUser;
}
