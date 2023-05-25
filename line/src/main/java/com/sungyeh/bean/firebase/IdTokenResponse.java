package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * IdTokenResponse
 *
 * @author sungyeh
 */
@Data
public class IdTokenResponse {
    private String kind;
    private String idToken;
    private String refreshToken;
    private String expiresIn;
    private String isNewUser;
}
