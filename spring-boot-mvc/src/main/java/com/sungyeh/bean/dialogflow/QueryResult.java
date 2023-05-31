package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * QueryResult
 *
 * @author sungyeh
 */
@Data
public class QueryResult implements Serializable {

    private static final long serialVersionUID = -3682978704185195939L;
    private String queryText;
    private String fulfillmentText;
    private Intent intent;
    private Map<String, String> parameters;
    private List<OutputContext> outputContexts;

}
