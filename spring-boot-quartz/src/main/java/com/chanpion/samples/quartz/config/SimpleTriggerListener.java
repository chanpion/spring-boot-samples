package com.chanpion.samples.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;
import org.springframework.stereotype.Component;

/**
 * 任务触发监听器
 *
 * @author April.Chen
 * @date 2024/1/4 11:07
 */
@Slf4j
@Component
public class SimpleTriggerListener extends TriggerListenerSupport {

    /**
     * Trigger监听器的名称
     *
     * @return
     */
    @Override
    public String getName() {
        return "mySimpleTriggerListener";
    }

    /**
     * Trigger被激发 它关联的job即将被运行
     *
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("myTriggerListener.triggerFired()");
    }

    /**
     * Trigger被激发 它关联的job即将被运行, TriggerListener 给了一个选择去否决 Job 的执行,如果返回TRUE 那么任务job会被终止
     *
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("myTriggerListener.vetoJobExecution()");
        return false;
    }

    /**
     * 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     * 那么有的触发器就有可能超时，错过这一轮的触发。
     *
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("myTriggerListener.triggerMisfired()");
    }

    /**
     * 任务完成时触发
     *
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.info("myTriggerListener.triggerComplete()");
    }
}