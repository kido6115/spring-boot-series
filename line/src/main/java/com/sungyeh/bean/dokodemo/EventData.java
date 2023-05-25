package com.sungyeh.bean.dokodemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * EventData
 *
 * @author sungyeh
 */
@Data
public class EventData {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("event_type_id")
    private Integer eventTypeId;
    @JsonProperty("seller_id")
    private Integer sellerId;
    @JsonProperty("lower_discount_rate")
    private Integer lowerDiscountRate;
    @JsonProperty("announcement_started_at")
    private String announcementStartedAt;
    @JsonProperty("started_at")
    private String startedAt;
    @JsonProperty("ended_at")
    private String endedAt;
    @JsonProperty("current_date_time")
    private String currentDateTime;

}
