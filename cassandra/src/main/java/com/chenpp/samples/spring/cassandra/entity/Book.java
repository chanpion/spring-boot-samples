package com.chenpp.samples.spring.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author April.Chen
 * @date 2023/10/25 4:27 下午
 **/
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @PrimaryKey
    private Long id;
    @Column("title")
    private String title;
    private String author;
}
