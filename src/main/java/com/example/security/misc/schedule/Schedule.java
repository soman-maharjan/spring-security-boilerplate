//package com.example.security.misc.schedule;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Schedule {
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void everyFiveSeconds() {
//        System.out.println(STR."Fixed rate task async - \{System.currentTimeMillis() / 1000}");
//    }
//
//    @Scheduled(fixedRate = 1000)
//    public void scheduleFixedRateTask() {
//        System.out.println(STR."Fixed rate task async - \{System.currentTimeMillis() / 1000}");
//    }
//
//    @Scheduled(fixedDelay = 1000, initialDelay = 2000)
//    public void scheduleInitialDelayTask() {
//        System.out.println(STR."Fixed rate task async - \{System.currentTimeMillis() / 1000}");
//    }
//}
