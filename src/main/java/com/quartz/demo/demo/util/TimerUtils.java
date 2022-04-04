package com.quartz.demo.demo.util;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
//import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public final class TimerUtils {
   

    private TimerUtils() {
    }

    public static JobDetail buildJobDetail(final Class<? extends Job> jobClass) {
        final JobDataMap jobDataMap = new JobDataMap();

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .withDescription("jobDescription")
                .setJobData(jobDataMap)
                .build();
    }
    
    public static Trigger buildTrigger(final Class<? extends Job> jobClass, String crnExpr) {
        //SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(crnExpr);
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis()))
                .build();
    }
    
}
