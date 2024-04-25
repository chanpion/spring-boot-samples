package com.chanpion.samples.quartz.dag;

/**
 * @author April.Chen
 * @date 2024/4/24 17:58
 */
public class TaskExecutor {

    public static void execute(TaskNode taskNode) {
        System.out.println("执行任务：" + taskNode.getId());
    }
}
