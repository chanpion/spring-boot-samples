package com.chanpion.samples.quartz.controller;

import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2024/4/25 11:17
 */
@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @Resource
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

    @GetMapping("/all")
    public Object getAll() {
        Set<ScheduledTask> tasks = scheduledAnnotationBeanPostProcessor.getScheduledTasks();

        return tasks.stream().map(task -> {
            Map<String, Object> info = new HashMap<>();
            Task t = task.getTask();
            info.put("type", t.getClass().getName());
            info.put("target", task.getTask().getRunnable().toString());
            if (t instanceof CronTask) {
                CronTask cronTask = (CronTask) task.getTask();
                info.put("cron", cronTask.getExpression());
            }
            return info;
        }).collect(Collectors.toSet());
    }

    public void add(){
    }
}
