package com.sungyeh.service;

/**
 * LineIntent
 *
 * @author sungyeh
 */
public enum LineIntent {

    /**
     * 意圖:天氣
     */
    WEATHER("!天氣"),
    /**
     * 意圖:你會甚麼
     */
    ASK("!你會甚麼");

    /**
     * 意圖
     */
    private final String intent;

    /**
     * LineIntent
     *
     * @param intent 意圖
     */
    LineIntent(String intent) {
        this.intent = intent;
    }

    /**
     * @return 意圖
     */
    public String getIntent() {
        return intent;
    }
}
