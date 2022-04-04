package com.quartz.demo.demo.scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.quartz.demo.demo.util.TimerUtils;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class SchedulerService {
    
    private final Scheduler scheduler;
    
    @Value("${testjob.trigger.cron}")
    private String crnExpr;
    
    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(final Class<? extends Job> jobClass) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, crnExpr);
        try {
            if (scheduler.checkExists(jobDetail.getKey())) {
                scheduler.deleteJob(jobDetail.getKey());
            }
            scheduler.scheduleJob(jobDetail, trigger);
            
        } catch (SchedulerException e) {
            log.error( "Errore Schedule" , e);
        }
    

    }

    

    @PostConstruct
    public void init() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("Errore Init", e);
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            log.error("Errore Shutdown", e);
        }
    }
}
