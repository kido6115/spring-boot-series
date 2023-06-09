package com.sungyeh.service;

/**
 * AsyncService
 *
 * @author sungyeh
 */
public interface AsyncService {
    void postToLine(String remoteAddress, String lat, String lng);
}
