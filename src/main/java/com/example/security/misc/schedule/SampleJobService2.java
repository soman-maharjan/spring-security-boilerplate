package com.example.security.misc.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SampleJobService2 {

    public static final long EXECUTION_TIME = 5000L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private AtomicInteger count = new AtomicInteger();

    public void executeSampleJob() {

        logger.info("The sample job 2 has begun...");
        try {
            Thread.sleep(EXECUTION_TIME);
        } catch (InterruptedException e) {
            logger.error("Error while executing sample job 2", e);
        } finally {
            count.incrementAndGet();
            logger.info("Sample job 2 has finished...");
        }
    }

    public int getNumberOfInvocations() {
        return count.get();
    }
}
