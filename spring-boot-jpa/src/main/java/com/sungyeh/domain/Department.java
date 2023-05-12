package com.sungyeh.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部門
 *
 * @author sungyeh
 */
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
public class Department extends AbstractEntity {

    /**
     * 部門名稱
     */
    private String name;
    /**
     * 部門代號
     */
    private String no;
    /**
     * 部門人員
     */
    @OneToMany(mappedBy = "department")
    private List<Person> persons;
}
