package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * RichContent
 *
 * @author sungyeh
 */
@Data
public class RichContent implements Serializable {

    private static final long serialVersionUID = 4043776822787406164L;
    private String title;
    private String subtitle;
    private String type;
    private List<String> text;
    private String rawUrl;
}
