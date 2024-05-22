package com.example.security.misc.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnotherJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SampleJobService2 jobService2;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        jobService2.executeSampleJob();

        logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }

}