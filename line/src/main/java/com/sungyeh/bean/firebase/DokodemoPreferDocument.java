package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * DokodemoPreferDocument
 *
 * @author sungyeh
 */
@Data
public class DokodemoPreferDocument {
    private String name;
    private Fields fields;
    private String createTime;
    private String updateTime;

    @Data
    public static class Fields {
        private Key key;
    }

    @Data
    public static class Key {
        private String stringValue;
    }
}
