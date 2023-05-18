package com.sungyeh.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

/**
 * 所有Entity的基礎型態
 *
 * @author sungyeh
 */
@MappedSuperclass
@EqualsAndHashCode(of = "id")
@Data
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 2698812789010477485L;

    /**
     * 主鍵
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
}
