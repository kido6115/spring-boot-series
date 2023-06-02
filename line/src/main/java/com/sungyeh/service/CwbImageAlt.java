package com.sungyeh.service;

/**
 * 氣象圖片代碼
 */
public enum CwbImageAlt {

    /**
     * 雷達alt屬性
     */
    RADAR("雷達"),
    /**
     * 雨量alt屬性
     */
    RAINFALL("雨量"),
    /**
     * 紫外線alt屬性
     */
    UV("紫外線"),
    /**
     * 溫度alt屬性
     */
    TEMPERATURE("溫度");

    /**
     * 代號
     */
    private final String alt;

    /**
     * @param alt 代號
     */
    CwbImageAlt(String alt) {
        this.alt = alt;
    }

    /**
     * @return 取得值
     */
    public String getAlt() {
        return alt;
    }
}
