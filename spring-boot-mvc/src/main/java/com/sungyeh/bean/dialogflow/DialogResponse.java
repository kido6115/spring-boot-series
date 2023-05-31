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

    private static final long serialVersionUID = -6918696980126253228L;
    List<Text> fulfillmentMessages;
}
