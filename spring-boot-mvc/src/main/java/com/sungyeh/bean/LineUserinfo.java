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
    /**
     * https://access.line.me. URL where the ID token is generated.
     */
    private String iss;
    /**
     * User ID for which the ID token is generated
     */
    private String sub;
    /**
     * Channel ID
     */
    private String aud;
    /**
     * The expiry date of the ID token in UNIX time.
     */
    private String exp;
    /**
     * Time when the ID token was generated in UNIX time.
     */
    private String iat;
    /**
     * The nonce value specified in the authorization URL.
     */
    private String nonce;
    /**
     * List of authentication methods used by the user. Not included in the payload under certain conditions.
     */
    private List<String> amr;
    /**
     * User's display name
     */
    private String name;
    /**
     * User's profile image URL.
     */
    private String picture;
    /**
     * User's email address.
     */
    private String email;
}
