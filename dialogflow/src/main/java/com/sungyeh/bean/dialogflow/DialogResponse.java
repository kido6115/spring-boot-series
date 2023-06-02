package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DialogResponse
 *
 * @author sungyeh
 */
@Data
public class DialogResponse implements Serializable {

    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = -6918696980126253228L;
    /**
     * fulfillmentMessages
     */
    List<Text> fulfillmentMessages;
}
