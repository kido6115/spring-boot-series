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
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("file_type_id")
    private Integer fileTypeId;

    @JsonProperty("path")
    private String path;
}
