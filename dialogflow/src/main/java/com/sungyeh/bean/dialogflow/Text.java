package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Text
 *
 * @author sungyeh
 */
@Data
public class Text implements Serializable {
    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = -3265409998310595057L;
    /**
     * text
     */
    private Texts text;
    /**
     * 訊息
     */
    private Map<String, List<List<RichContent>>> payload;

}
