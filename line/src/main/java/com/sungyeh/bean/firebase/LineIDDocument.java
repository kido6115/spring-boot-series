package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * DokodemoPreferDocument
 *
 * @author sungyeh
 */
@Data
public class LineIDDocument {
    /**
     * name
     */
    private String name;
    /**
     * fields
     */
    private Fields fields;
    /**
     * createTime
     */
    private String createTime;
    /**
     * updateTime
     */
    private String updateTime;

    /**
     * Fields
     */
    @Data
    public static class Fields {
        /**
         * openid
         */
        private OpenId openid;
    }

    /**
     *
     */
    @Data
    public static class OpenId {
        /**
         * stringValue
         */
        private String stringValue;
    }
}
