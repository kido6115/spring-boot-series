package com.sungyeh.service;

import java.util.List;

/**
 * 氣象局服務
 *
 * @author sugnyeh
 */
public interface CwbService {

    /**
     * 取得氣象局圖片
     *
     * @return 圖片
     */
    List<String> getImage();


}
