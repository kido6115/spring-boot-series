package com.sungyeh.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Broker DTO
 *
 * @author sungyeh
 */
@Data
@NoArgsConstructor
public class OutputMessage implements Serializable {

    /**
     * serialVersionUID
     * 這個屬性是為了在反序列化時確保類版本的兼容性。
     */
    private static final long serialVersionUID = 361495983377109328L;
    /**
     * 訊息產生時間
     */
    private long millSeconds;
    /**
     * 訊息
     */
    private Message message;

    /**
     * Constructor
     *
     * @param millSeconds 訊息產生時間
     * @param message     訊息
     */
    public OutputMessage(long millSeconds, Message message) {
        this.millSeconds = millSeconds;
        this.message = message;
    }
}
