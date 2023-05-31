package com.sungyeh.bean.dialogflow;

import lombok.Data;

import java.io.Serializable;

/**
 * Intent
 *
 * @author sungyeh
 */
@Data
public class Intent implements Serializable {


    private static final long serialVersionUID = -4728421499693234444L;
    private String name;
    private String displayName;
}
