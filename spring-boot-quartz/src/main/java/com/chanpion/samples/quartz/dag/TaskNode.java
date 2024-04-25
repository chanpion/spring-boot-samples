package com.chanpion.samples.quartz.dag;

import lombok.Data;

import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/4/24 17:19
 */
@Data
public class TaskNode {
    private String id;
    private String code;
    private String name;
    private boolean state;

    private Map<String, Object> params;

    public TaskNode() {
    }

    public TaskNode(String id) {
        this.id = id;
    }
}
