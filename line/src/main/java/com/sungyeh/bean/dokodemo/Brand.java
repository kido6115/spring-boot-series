package com.sungyeh.bean.dokodemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Brand
 *
 * @author sungyeh
 */
@Data
public class Brand {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_en")
    private String nameEn;


}
