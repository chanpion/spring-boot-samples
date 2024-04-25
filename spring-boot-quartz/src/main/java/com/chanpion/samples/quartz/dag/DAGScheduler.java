package com.chanpion.samples.quartz.dag;

/**
 * @author April.Chen
 * @date 2024/4/24 19:05
 */
public class DAGScheduler {

    public static Graph<TaskNode, TaskEdge> buidGraph() {
        Graph<TaskNode, TaskEdge> graph = new Graph<>();

        TaskNode task0 = new TaskNode("0");
        TaskNode task1 = new TaskNode("1");
        TaskNode task2 = new TaskNode("2");
        TaskNode task3 = new TaskNode("3");
        TaskNode task4 = new TaskNode("4");
        TaskNode task5 = new TaskNode("5");
        TaskNode task6 = new TaskNode("6");
        TaskNode task7 = new TaskNode("7");

        graph.addVertex(task0);
        graph.addVertex(task1);
        graph.addVertex(task3);
        graph.addVertex(task3);
        graph.addVertex(task4);
        graph.addVertex(task5);
        graph.addVertex(task6);
        graph.addVertex(task7);

        graph.addEdge(task1, task2);
        graph.addEdge(task1, task3);
        graph.addEdge(task3, task4);
        graph.addEdge(task2, task4);
        graph.addEdge(task4, task5);
        graph.addEdge(task4, task6);
        graph.addEdge(task6, task7);
        graph.addEdge(task0, task4);

        return graph;
    }

    public static void main(String[] args) {
        Graph<TaskNode, TaskEdge> graph = buidGraph();
        DAG dag = new DAG(graph);
        dag.process();
    }
}
