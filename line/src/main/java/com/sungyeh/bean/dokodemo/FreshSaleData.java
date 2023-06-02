package com.sungyeh.bean.dokodemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * FreshSaleData
 *
 * @author sungyeh
 */
@Data
public class FreshSaleData {

    /**
     * 識別碼
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 事件識別碼
     */
    @JsonProperty("event_id")
    private Integer eventId;

    /**
     * 名稱
     */
    @JsonProperty("name")
    private String name;

    /**
     * 價格
     */
    @JsonProperty("price")
    private Integer price;

    /**
     * 原價
     */
    @JsonProperty("reference_price")
    private Integer referencePrice;

    /**
     * 類別識別碼
     */
    @JsonProperty("category_id")
    private Integer categoryId;

    /**
     * 賣家代號
     */
    @JsonProperty("seller_id")
    private Integer sellerId;

    /**
     * 折扣比
     */
    @JsonProperty("discount_rate")
    private Integer discountRate;

    /**
     * skuId
     */
    @JsonProperty("sku_id")
    private Integer skuId;

    /**
     * 上市日
     */
    @JsonProperty("released_on")
    private String releasedOn;

    /**
     * 開始販售
     */
    @JsonProperty("sales_started_at")
    private String salesStartedAt;

    /**
     * 結束販售
     */
    @JsonProperty("sales_ended_at")
    private String salesEndedAt;

    /**
     * 庫存
     */
    @JsonProperty("quantity")
    private Integer quantity;

    /**
     * isNew
     */
    @JsonProperty("is_new")
    private Boolean isNew;

    /**
     * 媒體資訊
     */
    @JsonProperty("media_content")
    private List<MediaContent> mediaContents;

    /**
     * 品牌
     */
    @JsonProperty("brand")
    private Brand brand;
}
