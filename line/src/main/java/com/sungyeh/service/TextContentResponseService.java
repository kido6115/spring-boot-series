package com.sungyeh.service;


/**
 * 文字回應
 */
public interface TextContentResponseService extends ResponseService {

    /**
     * 此文本意圖
     *
     * @return 此文本意圖
     */
    String getIntent();

}
