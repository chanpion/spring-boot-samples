package com.chenpp.spring.samples.tkmybatis.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author April.Chen
 * @date 2023/4/26 1:34 下午
 **/
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    private Long id;
    private String username;
    private Date gmtCreate;
    private Date gmtModify;
}
