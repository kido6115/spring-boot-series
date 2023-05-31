package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Hook參數
 *
 * @author sungyeh
 */
@Data
public class QueryResult implements Serializable {
    /**
     * 這個屬性是為了在反序列化時確保類版本的兼容性
     */
    private static final long serialVersionUID = -3682978704185195939L;
    /**
     * 最後輸入文字
     */
    private String queryText;
    /**
     * 最後回應的文字
     */
    private String fulfillmentText;
    /**
     * 最後意圖
     */
    private Intent intent;
    /**
     * 最後參數
     */
    private Map<String, String> parameters;
    /**
     * 存活到最後的Context
     */
    private List<OutputContext> outputContexts;

}
