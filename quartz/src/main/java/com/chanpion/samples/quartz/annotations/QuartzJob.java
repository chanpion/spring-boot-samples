package com.chanpion.samples.quartz.annotations;

import java.lang.annotation.*;

/**
 * 定时任务注解
 *
 * @author April.Chen
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzJob {

    /**
     * job name
     */
    String name() default "";

    /**
     * job group
     */
    String group() default "";

    /**
     * job cron expression
     */
    String cron() default "";
}
