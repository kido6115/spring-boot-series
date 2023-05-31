package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;

/**
 * WebhookRequest
 *
 * @author sungyeh
 */
@Data
public class WebhookRequest implements Serializable {

    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = 1232395587585797040L;
    /**
     * session
     */
    private String session;
    /**
     * responseId
     */
    private String responseId;
    /**
     * queryResult
     */
    private QueryResult queryResult;
}
