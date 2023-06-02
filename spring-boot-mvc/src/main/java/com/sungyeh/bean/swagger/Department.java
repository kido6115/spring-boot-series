package com.sungyeh.bean.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 部門
 *
 * @author sungyeh
 */
@Data
@NoArgsConstructor
@Schema(description = "部門")
public class Department {


    /**
     * 主鍵
     */
    @Schema(description = "主鍵")
    private String id;
    /**
     * 部門名稱
     */
    @Schema(description = "部門名稱")
    private String name;
    /**
     * 部門代號
     */
    @Schema(description = "部門代號")
    private String no;

    /**
     * 建構子
     *
     * @param department 部門entity
     * @see com.sungyeh.domain.Department
     */
    public Department(com.sungyeh.domain.Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.no = department.getNo();
    }

    /**
     * 轉換成entity
     *
     * @return 部門entity
     * @see com.sungyeh.domain.Department
     */
    public com.sungyeh.domain.Department toEntity() {
        com.sungyeh.domain.Department department = new com.sungyeh.domain.Department();
        department.setId(this.id);
        department.setName(this.name);
        department.setNo(this.no);
        return department;
    }
}
