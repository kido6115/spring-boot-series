package com.sungyeh.bean.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 使用者資訊
 *
 * @author sungyeh
 */
@Data
@Schema(description = "產品")
public class Person {
    /**
     * 主鍵
     */
    @Schema(description = "主鍵")
    private String id;

    /**
     * 帳號
     */
    @Schema(description = "帳號")
    private String username;

    /**
     * 部門
     */
    @Schema(description = "部門")
    private Department department;
    /**
     * 角色
     */
    @Schema(description = "角色")
    private List<Role> roles;

}
