package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * OutputContext
 *
 * @author sungyeh
 */
@Data
public class OutputContext implements Serializable {

    private static final long serialVersionUID = 6039687173553022940L;

    private String name;

    private int lifespanCount;

    private Map<String, String> parameters;

}
