package com.sungyeh.bean.firebase;

import lombok.Data;

/**
 * LocationDocument
 *
 * @author sungyeh
 */
@Data
public class LocationDocument {
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

    public static LocationDocument builder(String ip, String lat, String lng) {
        LocationDocument document = new LocationDocument();
        Fields fields = new Fields();
        Ip openid = new Ip();
        openid.setStringValue(ip);
        fields.setIp(openid);
        Lat lat1 = new Lat();
        lat1.setStringValue(lat);
        fields.setLat(lat1);
        Lng lng1 = new Lng();
        lng1.setStringValue(lng);
        fields.setLng(lng1);
        document.setFields(fields);
        return document;
    }

    /**
     * Fields
     */
    @Data
    public static class Fields {
        /**
         * ip
         */
        private Ip ip;

        /**
         * lat
         */
        private Lat lat;
        /**
         * lng
         */
        private Lng lng;
    }

    /**
     *
     */
    @Data
    public static class Ip {
        /**
         * stringValue
         */
        private String stringValue;
    }

    @Data
    public static class Lat {
        /**
         * stringValue
         */
        private String stringValue;
    }

    @Data
    public static class Lng {
        /**
         * stringValue
         */
        private String stringValue;
    }
}
