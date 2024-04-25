package com.chanpion.samples.quartz.dag;

import lombok.Data;

/**
 * @author April.Chen
 * @date 2024/4/24 17:36
 */
@Data
public class Edge<V, E> {
    private String id;

    private Vertex<V> from;
    private Vertex<V> to;
    private E data;

    public Edge() {
    }

    public Edge(V from, V to) {
        this(from, to, null);
    }

    public Edge(V from, V to, E data) {
        this.from = new Vertex<>(from);
        this.to = new Vertex<>(to);
        this.data = data;
    }
}
