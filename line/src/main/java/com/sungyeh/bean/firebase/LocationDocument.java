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

    public static LocationDocument builder(String ip, String lat, String lng, String city) {
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
        City city1 = new City();
        city1.setStringValue(city);
        fields.setCity(city1);
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

        /**
         * city
         */
        private City city;

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

    @Data
    public static class City {
        /**
         * stringValue
         */
        private String stringValue;
    }
}
