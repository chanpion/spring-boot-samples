package com.chanpion.samples.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.stereotype.Component;

/**
 * 任务调度监听器
 *
 * @author April.Chen
 * @date 2024/1/4 11:06
 */
@Slf4j
@Component
public class SimpleSchedulerListener extends SchedulerListenerSupport {

    @Override
    public void jobScheduled(Trigger trigger) {
        log.info("任务被部署时被执行");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        log.info("任务被卸载时被执行");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        log.info("任务完成了它的使命，光荣退休时被执行");
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        log.info(triggerKey + "（一个触发器）被暂停时被执行");
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        log.info(triggerGroup + "所在组的全部触发器被停止时被执行");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        log.info(triggerKey + "（一个触发器）被恢复时被执行");
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        log.info(triggerGroup + "所在组的全部触发器被回复时被执行");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        log.info("一个JobDetail被动态添加进来");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        log.info(jobKey + "被删除时被执行");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        log.info(jobKey + "被暂停时被执行");
    }

    @Override
    public void jobsPaused(String jobGroup) {
        log.info(jobGroup + "(一组任务）被暂停时被执行");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        log.info(jobKey + "被恢复时被执行");
    }

    @Override
    public void jobsResumed(String jobGroup) {
        log.info(jobGroup + "(一组任务）被恢复时被执行");
    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {
        log.info("出现异常" + msg + "时被执行");
        cause.printStackTrace();
    }

    @Override
    public void schedulerInStandbyMode() {
        log.info("scheduler被设为standBy等候模式时被执行");
    }

    @Override
    public void schedulerStarted() {
        log.info("scheduler启动时被执行");
    }

    @Override
    public void schedulerStarting() {
        log.info("scheduler正在启动时被执行");
    }

    @Override
    public void schedulerShutdown() {
        log.info("scheduler关闭时被执行");
    }

    @Override
    public void schedulerShuttingdown() {
        log.info("scheduler正在关闭时被执行");
    }

    @Override
    public void schedulingDataCleared() {
        log.info("scheduler中所有数据包括jobs, triggers和calendars都被清空时被执行");
    }
}