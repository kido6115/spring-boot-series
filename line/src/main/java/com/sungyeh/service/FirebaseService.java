package com.sungyeh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sungyeh.bean.firebase.LocationDocument;

import java.util.List;

/**
 * Firebase 服務
 *
 * @author sungyeh
 */
public interface FirebaseService {

    /**
     * 取得ID token
     *
     * @return id token
     */
    String getIdToken();

    /**
     * 取得關鍵字
     *
     * @return 關鍵字
     */
    List<String> getKeywords();

    /**
     * 取得主動推撥使用者openid
     *
     * @return 使用者openid
     */
    List<String> getLineUsers();

    String addLocation(LocationDocument document) throws JsonProcessingException;
}
