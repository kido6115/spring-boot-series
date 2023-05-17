package com.sungyeh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * GoogleUserInfo
 *
 * @author sungyeh
 */
@Data
public class GoogleUserInfo {
    private String sub;
    private String picture;
    private String email;
    @JsonProperty("email_verified")
    private Boolean emailVerified;

}
