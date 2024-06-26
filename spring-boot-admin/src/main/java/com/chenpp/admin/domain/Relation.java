package com.chenpp.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/6/17 10:30
 */
@Data
public class Relation implements Serializable {
    private static final long serialVersionUID = -3620151711890089790L;

    private Long id;
    private String uid;
    private String name;
    private String label;
    private String startId;
    private String endId;
    private Map<String, Object> properties;
}
