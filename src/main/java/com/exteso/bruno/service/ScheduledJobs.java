package com.exteso.bruno.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledJobs {
    
    private static final long FIVE_MINUTES = 1000*60*5;
    
    private final FileService fileService;
    
    @Autowired
    public ScheduledJobs(FileService fileService) {
        this.fileService = fileService;
    }

    @Scheduled(fixedRate = FIVE_MINUTES, initialDelay = FIVE_MINUTES)
    public void removeUnusedFiles() {
        fileService.removeUnusedFilesOlderThan(DateUtils.addDays(new Date(), -1));
    }
}
