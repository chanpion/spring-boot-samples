package com.chanpion.samples.quartz.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author April.Chen
 * @date 2024/1/4 10:31
 */
@Slf4j
@EnableScheduling
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> log.info("Schedule now is: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    String cron = "0 1 * * * *";
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );

    }

    private ScheduledExecutorService taskExecutor() {
        return  Executors.newScheduledThreadPool(10);
    }

    @Bean("abc3")
    public TaskScheduler oneDayTbScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("task-3-");
        scheduler.setPoolSize(10);
        scheduler.initialize();
        return scheduler;
    }

//    @Bean
//    public ThreadPoolTaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);
//        executor.setMaxPoolSize(50);
//        executor.setQueueCapacity(1000);
//        executor.setKeepAliveSeconds(60);
//        executor.setThreadNamePrefix("task-executor-");
//        return executor;
//    }


    /**
     * 每5秒执行一次
     */
    @Scheduled(cron = "0/5 * *  * * ? ")
    public void execute() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("The time is now {}", df.format(new Date()));
    }

    /**
     * 同步
     * 每隔两分钟执行一次
     */
    @Async("abc3")
    @Scheduled(cron = "0 0/2 * * * ?")
    public void asynTb() {
        //业务代码
        log.info("--------------begin-------------end-----------");
    }
}
