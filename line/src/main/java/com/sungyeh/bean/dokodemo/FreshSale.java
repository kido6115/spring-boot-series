package com.sungyeh.bean.dokodemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * FreshSale
 *
 * @author sungyeh
 */
@Data
public class FreshSale {

    @JsonProperty("data")
    List<FreshSaleData> data;

}
