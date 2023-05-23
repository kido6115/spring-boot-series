package com.sungyeh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * LineAuthorizationCodeResponse
 *
 * @author sungyeh
 */
@Data
public class OauthAuthorizationCodeResponse {
    /**
     * access token
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 過期時間
     */
    @JsonProperty("expires_in")
    private String expiresIn;
    /**
     * refresh token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     * scope
     */
    private String scope;
    /**
     * Bearer
     */
    @JsonProperty("token_type")
    private String tokenType;
}
