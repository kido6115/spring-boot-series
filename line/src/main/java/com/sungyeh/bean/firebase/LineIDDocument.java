package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * DokodemoPreferDocument
 *
 * @author sungyeh
 */
@Data
public class LineIDDocument {
    private String name;
    private Fields fields;
    private String createTime;
    private String updateTime;

    @Data
    public static class Fields {
        private OpenId openid;
    }

    @Data
    public static class OpenId {
        private String stringValue;
    }
}
