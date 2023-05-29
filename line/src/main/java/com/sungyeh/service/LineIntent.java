package com.sungyeh.service;

/**
 * LineIntent
 *
 * @author sungyeh
 */
public enum LineIntent {

    WEATHER("!天氣"), ASK("!你會甚麼");

    private final String intent;

    LineIntent(String intent) {
        this.intent = intent;
    }

    public String getIntent() {
        return intent;
    }
}
