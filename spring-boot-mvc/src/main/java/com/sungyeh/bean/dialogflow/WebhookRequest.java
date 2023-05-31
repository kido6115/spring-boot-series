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


    private static final long serialVersionUID = 1232395587585797040L;
    private String session;

    private String responseId;

    private QueryResult queryResult;
}
