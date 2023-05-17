package com.sungyeh.bean;

import lombok.Data;

import java.util.List;

/**
 * LineUserinfo
 *
 * @author sungyeh
 */
@Data
public class LineUserinfo {
    private String iss;
    private String sub;
    private String aud;
    private String exp;
    private String iat;
    private String nonce;
    private List<String> amr;
    private String name;
    private String picture;
    private String email;
}
