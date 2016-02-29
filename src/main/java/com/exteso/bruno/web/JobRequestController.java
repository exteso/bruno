package com.exteso.bruno.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exteso.bruno.model.FailureType;
import com.exteso.bruno.model.JobRequest;
import com.exteso.bruno.model.JobRequestCreation;
import com.exteso.bruno.model.RequestType;
import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.service.JobRequestService;

@RestController
public class JobRequestController {

    private final JobRequestService jobRequestService;

    @Autowired
    public JobRequestController(JobRequestService jobRequestService) {
        this.jobRequestService = jobRequestService;
    }

    @RequestMapping(value = "/api/job-request", method = RequestMethod.POST)
    public void createRequest(@RequestBody JobRequestCreation jobRequest, Principal principal) {
        jobRequestService.create(jobRequest, UserIdentifier.from(principal), new Date());
    }
    
    @RequestMapping("/api/job-request/{id}")
    public JobRequest findById(@PathVariable("id") long id) {
        return jobRequestService.findById(id);
    }

    @RequestMapping("/api/job-request/list")
    public List<JobRequest> findAllForUser(Principal principal) {
        return jobRequestService.findForUser(UserIdentifier.from(principal));
    }
    
    @RequestMapping("/api/job-request/list-for-service-provider")
    public List<JobRequest> findAllForServiceProvider(Principal principal) {
        return jobRequestService.findForServiceProvider(UserIdentifier.from(principal));
    }

    @RequestMapping("/api/job-request/failure-type")
    public List<String> failureType() {
        return Arrays.asList(FailureType.values()).stream().map(Enum::name).collect(Collectors.toList());
    }

    @RequestMapping("/api/job-request/request-type")
    public List<String> requestType() {
        return Arrays.asList(RequestType.values()).stream().map(Enum::name).collect(Collectors.toList());
    }

    //

}
