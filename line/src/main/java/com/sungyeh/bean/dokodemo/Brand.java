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

    /**
     * 識別碼
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 名稱
     */
    @JsonProperty("name")
    private String name;

    /**
     * 英文名稱
     */
    @JsonProperty("name_en")
    private String nameEn;


}
