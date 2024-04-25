package com.chanpion.samples.quartz.dag;

import lombok.Data;

import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/4/24 17:20
 */
@Data
public class TaskEdge {

    private TaskNode from;
    private TaskNode to;

    private Map<String, Object> params;
}
