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
import com.exteso.bruno.model.JobRequestBid;
import com.exteso.bruno.model.JobRequestBidModification;
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
        return jobRequestService.findOpenForServiceProvider(UserIdentifier.from(principal));
    }
    
    @RequestMapping("/api/job-request/list-accepted-for-service-provider")
    public List<JobRequest> findAcceptedForServiceProvider(Principal principal) {
        return jobRequestService.findAcceptedForServiceProvider(UserIdentifier.from(principal));
    }

    @RequestMapping("/api/job-request/failure-type")
    public List<String> failureType() {
        return Arrays.asList(FailureType.values()).stream().map(Enum::name).collect(Collectors.toList());
    }

    @RequestMapping("/api/job-request/request-type")
    public List<String> requestType() {
        return Arrays.asList(RequestType.values()).stream().map(Enum::name).collect(Collectors.toList());
    }
    
    @RequestMapping(value = "/api/job-request/{id}/bid-list", method = RequestMethod.GET)
    public List<JobRequestBid> showAllBids(@PathVariable("id") long id, Principal principal) {
        //only the creation user of the request can see this
        return jobRequestService.findAllBids(id, UserIdentifier.from(principal));
    }
    
    
    @RequestMapping(value = "/api/job-request/{id}/bid", method = RequestMethod.GET)
    public JobRequestBid showCurrentServiceProviderOffer(@PathVariable("id") long id, Principal principal) {
        //FIXME only provider can do that
        return jobRequestService.findBy(id, UserIdentifier.from(principal)).orElse(null);
    }
    
    @RequestMapping(value = "/api/job-request/{id}/bid", method = RequestMethod.POST)
    public void createOrUpdateOffer(@PathVariable("id") long id, @RequestBody JobRequestBidModification offer, Principal principal) {
        jobRequestService.createOrUpdateBid(id, offer, UserIdentifier.from(principal));
        //FIXME only provider can do that
    }
    //
    
    @RequestMapping(value = "/api/job-request/{id}/bid/{userId}/accept", method = RequestMethod.POST)
    public void acceptBid(@PathVariable("id") long id, @PathVariable("userId") long userId, Principal principal) {
        jobRequestService.acceptBid(id, userId, UserIdentifier.from(principal));
    }

}
