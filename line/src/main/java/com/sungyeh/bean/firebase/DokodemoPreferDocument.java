package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * DokodemoPreferDocument
 *
 * @author sungyeh
 */
@Data
public class DokodemoPreferDocument {
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
     * fields
     */
    @Data
    public static class Fields {
        /**
         * key
         */
        private Key key;
    }

    /**
     * key
     */
    @Data
    public static class Key {
        /**
         * stringValue
         */
        private String stringValue;
    }
}
