package com.sungyeh.service;

public enum CwbImageAlt {
    RADAR("雷達"),
    RAINFALL("雨量"),
    UV("紫外線"),
    TEMPERATURE("溫度");

    private final String alt;

    CwbImageAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        return alt;
    }
}
