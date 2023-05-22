package com.sungyeh.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OutputMessage implements Serializable {

    private static final long serialVersionUID = 361495983377109328L;
    private long millSeconds;
    private Message message;

    public OutputMessage(long millSeconds, Message message) {
        this.millSeconds = millSeconds;
        this.message = message;
    }
}
