package com.sungyeh.bean.dokodemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * MediaContent
 *
 * @author sungyeh
 */
@Data
public class MediaContent {
    /**
     * 識別碼
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * fileTypeId
     */
    @JsonProperty("file_type_id")
    private Integer fileTypeId;

    /**
     * 圖片路徑
     */
    @JsonProperty("path")
    private String path;
}
