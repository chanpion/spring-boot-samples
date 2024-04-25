package com.chanpion.samples.quartz.dag;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/4/24 17:34
 */
public class Graph<V, E> {

    /**
     *
     */
    private Map<V, Vertex<V>> vertices;

    /**
     * a -> b, key : b
     */
    private Map<V, List<Edge<V, E>>> inEdges;
    /**
     * a -> b, key : a
     */
    private Map<V, List<Edge<V, E>>> outEdges;

    public Graph() {
        vertices = new ConcurrentHashMap<>();
        inEdges = new ConcurrentHashMap<>();
        outEdges = new ConcurrentHashMap<>();
    }

    private Collection<Vertex<V>> getVertices() {
        return vertices.values();
    }

    public void addVertex(V v) {
        vertices.put(v, new Vertex<>(v));

    }

    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    public void addEdge(V from, V to, E data) {
        Edge<V, E> edge = new Edge<>(from, to, data);
        if (!outEdges.containsKey(from)) {
            outEdges.put(from, new ArrayList<>());
        }
        if (!inEdges.containsKey(to)) {
            inEdges.put(to, new ArrayList<>());
        }
        inEdges.get(to).add(edge);
        outEdges.get(from).add(edge);
    }

    public Collection<Vertex<V>> getBeginVertices() {
        return CollectionUtils.subtract(vertices.keySet(), inEdges.keySet()).stream()
                .map(v -> vertices.get(v)).
                collect(Collectors.toSet());
    }

    public Collection<Vertex<V>> getEndVertices() {
        return CollectionUtils.subtract(vertices.keySet(), outEdges.keySet()).stream()
                .map(v -> vertices.get(v)).
                collect(Collectors.toSet());
    }

    public Collection<Vertex<V>> getParents(Vertex<V> vertex) {
        if (!inEdges.containsKey(vertex.getData())) {
            return Collections.emptySet();
        }
        return inEdges.get(vertex.getData()).stream().map(Edge::getFrom).collect(Collectors.toSet());

    }

    public Collection<Vertex<V>> getChildren(Vertex<V> vertex) {
        if (!outEdges.containsKey(vertex.getData())) {
            return Collections.emptySet();
        }
        return outEdges.get(vertex.getData()).stream().map(Edge::getTo).collect(Collectors.toSet());
    }
}
