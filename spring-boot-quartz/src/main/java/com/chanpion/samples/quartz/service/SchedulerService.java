package com.chanpion.samples.quartz.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author April.Chen
 * @date 2024/1/4 10:19
 */
@Slf4j
@Service
public class SchedulerService {

    @Resource
    private SchedulerFactoryBean schedulerFactory;

    /**
     * 新增定时任务
     *
     * @param jobName   定时任务名称id同一个组中 唯一
     * @param groupName 组名
     * @param timeout   多久后执行 单位是秒
     * @param data      需要传递的数据
     * @param clazz     需要执行的job
     * @return
     */
    public boolean addJob(String jobName, String groupName, Integer timeout, Map<String, Object> data, Class<? extends Job> clazz) {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = new TriggerKey(jobName, groupName);
        Date triggerStartTime = new Date(System.currentTimeMillis() + timeout * 1000);
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(triggerStartTime).build();
        JobKey jobKey = new JobKey(jobName, groupName);
        JobDetail job = JobBuilder.newJob(clazz)
                .withIdentity(jobKey).usingJobData(new JobDataMap(data))
                .requestRecovery(true).build();
        try {
            scheduler.scheduleJob(job, trigger);
            return true;
        } catch (SchedulerException e) {
            log.error("occur an exception when to add a job for:{}", e.getMessage());
            return false;
        }
    }


    /**
     * 新增定时任务
     *
     * @param jobName   定时任务名称id同一个组中 唯一
     * @param groupName 组名
     * @param startTime 触发时间
     * @param data      需要传递的数据
     * @param clazz     需要执行的job
     * @return
     */
    public boolean addJob(String jobName, String groupName, Date startTime, Map<String, Object> data, Class<? extends Job> clazz) {
        String dataJson = JSON.toJSONString(data);
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = new TriggerKey(jobName, groupName);
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(startTime).build();
        JobKey jobKey = new JobKey(jobName, groupName);
        JobDetail job = JobBuilder.newJob(clazz)
                .withIdentity(jobKey).usingJobData(new JobDataMap(data))
                .requestRecovery(true).build();
        try {
            scheduler.scheduleJob(job, trigger);
            return true;
        } catch (ObjectAlreadyExistsException e) {
            log.warn("repeated job for:{}, jobName:{},groupName:{}, ignore it if your applications are multiple instances", e, jobName, groupName);
            return false;
        } catch (SchedulerException e) {
            log.error("occur an exception when to add a job for:{}", e.getMessage());
            log.error("add job failed data:{}", dataJson);
            return false;
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName   定时任务名称id同一个组中 唯一
     * @param groupName 组名
     */
    public void pauseJob(String jobName, String groupName) {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDataMap jobDataMap = trigger.getJobDataMap();
            Date endTime = trigger.getStartTime();
            Long duringTime = endTime.getTime() - System.currentTimeMillis();
            jobDataMap.put("quartz_pause_time", duringTime);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).usingJobData(jobDataMap).build();
            scheduler.rescheduleJob(triggerKey, trigger);

            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("occur an exception when to pause a job for:{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName   定时任务名称id同一个组中 唯一
     * @param groupName 组名
     */
    public void resumeJob(String jobName, String groupName) {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            JobDataMap jobDataMap = trigger.getJobDataMap();
            Long duringTime = (Long) jobDataMap.get("quartz_pause_time");
            Date triggerStartTime = new Date(System.currentTimeMillis() + duringTime);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).startAt(triggerStartTime).build();

            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("resume job error", e);
        }
    }

    /**
     * 删除任务
     *
     * @param jobName   定时任务名称id同一个组中 唯一
     * @param groupName 组名
     */
    public void removeJob(String jobName, String groupName) {
        Scheduler scheduler = schedulerFactory.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
        JobKey jobKey = JobKey.jobKey(jobName, groupName);
        // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        try {
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("occur an exception when to remove a job for:{}", e.getMessage());
        }
    }
}
