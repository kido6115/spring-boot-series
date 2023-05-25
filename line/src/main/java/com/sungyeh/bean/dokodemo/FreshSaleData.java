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

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("event_id")
    private Integer eventId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("reference_price")
    private Integer referencePrice;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("seller_id")
    private Integer sellerId;

    @JsonProperty("discount_rate")
    private Integer discountRate;

    @JsonProperty("sku_id")
    private Integer skuId;

    @JsonProperty("released_on")
    private String releasedOn;

    @JsonProperty("sales_started_at")
    private String salesStartedAt;

    @JsonProperty("sales_ended_at")
    private String salesEndedAt;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("is_new")
    private Boolean isNew;

    @JsonProperty("media_content")
    private List<MediaContent> mediaContents;

    @JsonProperty("brand")
    private Brand brand;
}
