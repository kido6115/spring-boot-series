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
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("id_token")
    private String idToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String scope;
    @JsonProperty("token_type")
    private String tokenType;
}
