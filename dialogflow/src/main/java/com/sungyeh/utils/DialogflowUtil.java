package com.sungyeh.utils;


import com.sungyeh.bean.dialogflow.DialogResponse;
import com.sungyeh.bean.dialogflow.RichContent;
import com.sungyeh.bean.dialogflow.Text;
import com.sungyeh.bean.dialogflow.Texts;

import java.util.*;

/**
 * DialogflowUtil
 *
 * @author sungyeh
 */
final public class DialogflowUtil {

    /**
     * @param richContents richContents
     * @return DialogResponse
     */
    public static DialogResponse createFulfillmentCustomPayload(List<RichContent> richContents) {
        DialogResponse dialogResponse = new DialogResponse();
        Text text = new Text();
        Map<String, List<List<RichContent>>> payload = new HashMap<>();
        payload.put("richContent", Collections.singletonList(richContents));
        text.setPayload(payload);
        dialogResponse.setFulfillmentMessages(Collections.singletonList(text));
        return dialogResponse;
    }

    /**
     * @param text text
     * @return DialogResponse
     */
    public static DialogResponse createFulfillmentText(List<String> text) {
        DialogResponse dialogResponse = new DialogResponse();
        List<Text> fulfillmentMessages = new ArrayList<>();
        Text text1 = new Text();
        Texts texts1 = new Texts();
        texts1.setText(text);
        text1.setText(texts1);
        fulfillmentMessages.add(text1);
        dialogResponse.setFulfillmentMessages(fulfillmentMessages);
        return dialogResponse;
    }
}
