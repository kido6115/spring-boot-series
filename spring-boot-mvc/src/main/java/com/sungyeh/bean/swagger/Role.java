package com.sungyeh.bean.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色
 *
 * @author sungyeh
 */
@Data
@NoArgsConstructor
@Schema(description = "角色")
public class Role {
    /**
     * 主鍵
     */
    @Schema(description = "主鍵")
    private String id;
    /**
     * 角色名稱
     */
    @Schema(description = "角色名稱")
    private String name;
    /**
     * 角色代號
     */
    @Schema(description = "角色代號")
    private String no;

    /**
     * 建構子
     *
     * @param role 角色entity
     * @see com.sungyeh.domain.Role
     */
    public Role(com.sungyeh.domain.Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.no = role.getNo();
    }

    /**
     * 轉換成entity
     *
     * @return 角色entity
     * @see com.sungyeh.domain.Role
     */
    public com.sungyeh.domain.Role toEntity() {
        com.sungyeh.domain.Role role = new com.sungyeh.domain.Role();
        role.setId(this.id);
        role.setName(this.name);
        role.setNo(this.no);
        return role;
    }
}
