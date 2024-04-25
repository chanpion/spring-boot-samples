package com.chanpion.samples.quartz.config;

import com.chanpion.samples.quartz.jobs.BaseJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * @author April.Chen
 * @date 2024/1/4 10:08
 */
@Configuration
public class QuartzConfig implements InitializingBean {

    @Resource
    private Scheduler scheduler;


    @Bean
    public JobDetail testQuartz1() {
        return JobBuilder.newJob(BaseJob.class).withIdentity("testTask1").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        //5秒执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("testTask1")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail testQuartz2() {
        return JobBuilder.newJob(BaseJob.class).withIdentity("testTask2").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger2() {
        //cron方式，每隔5秒执行一次
        return TriggerBuilder.newTrigger().forJob(testQuartz2())
                .withIdentity("testTask2")
                .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobDetail() {
        JobDetail jobDetail = JobBuilder.newJob(BaseJob.class)
                //是否持久化
                .storeDurably(true)
                .withDescription("Job任务描述")
                //任务名称和任务分组 组合成任务唯一标识
                .withIdentity(JobKey.jobKey("JobName", "JobGroup"))
                .usingJobData("jobDataKey", "jobDataValue")
                .build();
        return jobDetail;
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        Trigger trigger = TriggerBuilder.newTrigger() //创建一个定义或者构建触发器的builder实例
                .forJob(jobDetail) //通过从给定的作业中提取出jobKey,设置由生成的触发器触发的作业的标识
                //自定义触发器描述
                .withDescription("自定义触发器描述")
                //设置触发器名称、触发器分组，组合为触发器唯一标识
                .withIdentity(TriggerKey.triggerKey("traggerName", "traggerGroup"))
                //如果有一个Job任务供多个触发器调度，而每个触发器调度传递不同的参数，此时JobDataMap可以提供不同的数据输入
                //在任务执行时JobExecutionContext提供不同的JobDataMap参数给Job
                .usingJobData("tragger_param", "tragger_value")
                //触发器优先级
                .withPriority(6)
                //设置用于定义触发器的{@link org.quartz.ScheduleBuilder}配置计划
                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ? "))
                //将触发器的启动时间设置为当前时刻，触发器可能此时触发，也可能不触发，这取决于为触发器配置的计划
                .startNow()
                //构造触发器
                .build();

        return trigger;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduler.getListenerManager().addJobListener(new SimpleJobListener());
        scheduler.getListenerManager().addSchedulerListener(new SimpleSchedulerListener());
        scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener());
    }
}