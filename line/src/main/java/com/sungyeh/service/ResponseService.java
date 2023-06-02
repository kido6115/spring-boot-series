package com.sungyeh.service;

import com.linecorp.bot.model.message.Message;

/**
 * 回傳訊息基礎介面
 */
public interface ResponseService {

    /**
     * 建立訊息
     *
     * @return 訊息
     */
    Message bulidMessage();

}
