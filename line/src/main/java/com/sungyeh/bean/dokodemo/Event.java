package com.sungyeh.bean.dokodemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Event
 *
 * @author sungyeh
 */
@Data
public class Event {

    /**
     * 事件集
     */
    @JsonProperty("data")
    private List<EventData> eventData;
}
