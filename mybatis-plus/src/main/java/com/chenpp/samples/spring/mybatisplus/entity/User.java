package com.chenpp.samples.spring.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author April.Chen
 * @date 2023/4/27 11:56 上午
 **/
@Data
@TableName("user")
public class User {
    @TableId
    private Long id;
    @TableField("name")
    private String name;
    private Integer age;
    private String email;
}
