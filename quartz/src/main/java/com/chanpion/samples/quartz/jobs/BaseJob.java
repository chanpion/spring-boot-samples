package com.chanpion.samples.quartz.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BaseJob extends QuartzJobBean {
    private static Logger logger = LoggerFactory.getLogger(BaseJob.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    }
}
