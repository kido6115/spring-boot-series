package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;

/**
 * 意圖
 *
 * @author sungyeh
 */
@Data
public class Intent implements Serializable {


    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = -4728421499693234444L;
    /**
     * name
     */
    private String name;
    /**
     * displayName
     */
    private String displayName;
}
