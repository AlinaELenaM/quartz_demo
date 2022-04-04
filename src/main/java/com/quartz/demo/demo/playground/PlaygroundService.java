package com.quartz.demo.demo.playground;

import com.quartz.demo.demo.jobs.TestJob;
import com.quartz.demo.demo.scheduler.SchedulerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaygroundService {
    private final SchedulerService scheduler;

    @Autowired
    public PlaygroundService( final SchedulerService scheduler) {
        this.scheduler = scheduler;
    }

    public void runTestJob() {
        scheduler.schedule(TestJob.class);

    }
    
    
}
