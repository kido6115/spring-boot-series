package com.sungyeh.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable {

    private static final long serialVersionUID = -6043979607775808129L;
    private String from;
    private String text;

}
