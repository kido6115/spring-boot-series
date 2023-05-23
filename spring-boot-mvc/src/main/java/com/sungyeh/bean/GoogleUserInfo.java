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
    /**
     * google openid
     */
    private String id;
    /**
     * 相片網址
     */
    private String picture;
    /**
     * 信箱
     */
    private String email;
    /**
     * 是否已認證過信箱
     */
    @JsonProperty("verified_email")
    private Boolean emailVerified;

}
