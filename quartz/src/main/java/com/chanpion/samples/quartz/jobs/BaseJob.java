package com.chanpion.samples.quartz.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author April.Chen
 */

/**
 * 持久化
 */
@PersistJobDataAfterExecution
/**
 * 禁止并发执行
 */
@DisallowConcurrentExecution
public class BaseJob extends QuartzJobBean {
    private static Logger logger = LoggerFactory.getLogger(BaseJob.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("TestQuartz02----" + sdf.format(new Date()));
    }
}
