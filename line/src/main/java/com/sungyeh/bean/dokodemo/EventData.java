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

    /**
     * 識別碼
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 事件類型
     */
    @JsonProperty("event_type_id")
    private Integer eventTypeId;

    /**
     * 賣家代號
     */
    @JsonProperty("seller_id")
    private Integer sellerId;

    /**
     * lower_discount_rate
     */
    @JsonProperty("lower_discount_rate")
    private Integer lowerDiscountRate;

    /**
     * 釋出事件時間
     */
    @JsonProperty("announcement_started_at")
    private String announcementStartedAt;

    /**
     * 事件開始時間
     */
    @JsonProperty("started_at")
    private String startedAt;

    /**
     * 事件結束時間
     */
    @JsonProperty("ended_at")
    private String endedAt;

    /**
     * 目前時間
     */
    @JsonProperty("current_date_time")
    private String currentDateTime;

}
