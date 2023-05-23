package com.sungyeh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * LineAuthorizationCodeResponse
 *
 * @author sungyeh
 */
@Data
public class LineAuthorizationCodeResponse {
    /**
     * Access token. Valid for 30 days.
     */
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * Number of seconds until the access token expires.
     */
    @JsonProperty("expires_in")
    private String expiresIn;
    /**
     * line openid
     */
    @JsonProperty("id_token")
    private String idToken;
    /**
     * Token used to get a new access token (refresh token). Valid for 90 days after the access token is issued.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /**
     * Permissions granted to the access token. For more information on scopes
     */
    private String scope;
    /**
     * Bearer
     */
    @JsonProperty("token_type")
    private String tokenType;
}
