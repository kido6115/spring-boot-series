package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Texts
 *
 * @author sungyeh
 */
@Data
public class Texts implements Serializable {
    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = 1587702544007290025L;
    /**
     * text
     */
    List<String> text;
}
