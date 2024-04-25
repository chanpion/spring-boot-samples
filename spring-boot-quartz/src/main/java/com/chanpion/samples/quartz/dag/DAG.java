package com.chanpion.samples.quartz.dag;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author April.Chen
 * @date 2024/4/24 17:24
 */
@Data
public class DAG {
    private Graph<TaskNode, TaskEdge> graph;

    private Set<TaskNode> finishedSet = new HashSet<>();

    private Queue<Vertex<TaskNode>> queue = new LinkedList<>();

    public DAG(Graph<TaskNode, TaskEdge> graph) {
        this.graph = graph;
    }


    public void process() {
        Collection<Vertex<TaskNode>> beginVertices = graph.getBeginVertices();
        beginVertices.forEach(vertex -> queue.offer(vertex));
        while (!queue.isEmpty()) {
            Vertex<TaskNode> vertex = queue.poll();
            if (finishedSet.contains(vertex.getData())) {
                continue;
            }
            Collection<Vertex<TaskNode>> parents = graph.getParents(vertex);
            boolean allParentFinished = parents.stream().allMatch(parent -> finishedSet.contains(parent.getData()));
            if (CollectionUtils.isEmpty(parents) || allParentFinished) {
                TaskExecutor.execute(vertex.getData());
                finishedSet.add(vertex.getData());
                Collection<Vertex<TaskNode>> children = graph.getChildren(vertex);
                children.forEach(v -> queue.offer(v));
            } else {
                queue.offer(vertex);
            }
        }

    }
}
