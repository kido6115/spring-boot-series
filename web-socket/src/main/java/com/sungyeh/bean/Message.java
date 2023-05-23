package com.sungyeh.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 訊息主體
 *
 * @author sungyeh
 */
@Data
public class Message implements Serializable {
    /**
     * serialVersionUID
     * 這個屬性是為了在反序列化時確保類版本的兼容性。
     */
    private static final long serialVersionUID = -6043979607775808129L;
    /**
     * 來源
     */
    private String from;
    /**
     * 內文
     */
    private String text;

}
