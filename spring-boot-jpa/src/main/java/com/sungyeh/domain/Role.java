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

    private String name;

    private String no;

}
