package com.chenpp.admin.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/6/17 10:29
 */
@Data
public class Entity implements Serializable {
    private static final long serialVersionUID = -9074750755279573870L;

    private Serializable id;
    private String uid;
    private String label;
    private String name;
    private Map<String, Object> properties;

}
