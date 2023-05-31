package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 機器人收集來的參數
 *
 * @author sungyeh
 */
@Data
public class OutputContext implements Serializable {
    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = 6039687173553022940L;

    /**
     * name
     */
    private String name;
    /**
     * 生命週期
     */
    private int lifespanCount;

    /**
     * 參數
     */
    private Map<String, String> parameters;

}
