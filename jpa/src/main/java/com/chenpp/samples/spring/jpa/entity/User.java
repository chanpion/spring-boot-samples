package com.chenpp.samples.spring.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author April.Chen
 * @date 2023/10/17 6:32 下午
 **/
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    private Integer age;
//    @Transient
    private String email;
}
