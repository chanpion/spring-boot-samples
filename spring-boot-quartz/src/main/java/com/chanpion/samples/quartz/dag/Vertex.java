package com.chanpion.samples.quartz.dag;

import lombok.Data;

import java.util.List;

/**
 * @author April.Chen
 * @date 2024/4/24 17:29
 */
@Data
public class Vertex<V> {
    private String id;
    private V data;

    public Vertex() {
    }

    public Vertex(V v) {
        this.data = v;
    }
}
