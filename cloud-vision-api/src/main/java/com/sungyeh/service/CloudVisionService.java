package com.sungyeh.service;

/**
 * CloudVisionService
 *
 * @author sungyeh
 */
public interface CloudVisionService {
    /**
     * 傳送圖片至cloud vision
     *
     * @param para image base64 string
     * @return json string
     */
    String send(String para);
}
