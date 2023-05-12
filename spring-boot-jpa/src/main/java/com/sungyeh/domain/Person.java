package com.sungyeh.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 使用者資訊
 *
 * @author sungyeh
 */
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Person extends AbstractEntity {

    /**
     * 帳號
     */
    private String username;
    /**
     * 密碼
     */
    private String password;
    /**
     * 部門
     */
    @ManyToOne
    @JoinColumn(name = "department", foreignKey = @ForeignKey(name = "fk_person_department"))
    private Department department;
    /**
     * 角色
     */
    @ManyToMany
    @JoinTable(name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "role_id"}))
    private List<Role> roles;

}
