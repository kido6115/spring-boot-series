package com.sungyeh.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 *
 * @author sungyeh
 */
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Role extends AbstractEntity {

    /**
     * 角色名稱
     */
    private String name;

    /**
     * 角色代號
     */
    private String no;

}
