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

    private static final long serialVersionUID = -3265409998310595057L;
    private Texts text;
    private Map<String, List<List<RichContent>>> payload;

}
