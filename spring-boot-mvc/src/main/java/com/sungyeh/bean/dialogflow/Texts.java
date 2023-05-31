package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Texts
 *
 * @author sungyeh
 */
@Data
public class Texts implements Serializable {

    private static final long serialVersionUID = 1587702544007290025L;
    List<String> text;
}
