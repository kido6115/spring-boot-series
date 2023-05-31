package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 回復訊息樣板
 *
 * @author sungyeh
 */
@Data
public class RichContent implements Serializable {
    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = 4043776822787406164L;
    /**
     * 標題
     */
    private String title;
    /**
     * 副標題
     */
    private String subtitle;
    /**
     * 回復樣板類型
     */
    private String type;
    /**
     * 文字內容
     */
    private List<String> text;
    /**
     * 圖片網址
     */
    private String rawUrl;
}
