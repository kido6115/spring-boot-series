package com.sungyeh.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 訂房
 *
 * @author sungyeh
 */
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Booking extends AbstractEntity {

    /**
     * 人數
     */
    private Integer people;
    /**
     * 預定時間
     */
    private LocalDateTime dateTime;

    /**
     * 建立時間
     */
    private LocalDateTime createTime;
}
