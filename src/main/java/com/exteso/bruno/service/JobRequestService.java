package com.exteso.bruno.service;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exteso.bruno.model.FailureType;
import com.exteso.bruno.model.JobRequest;
import com.exteso.bruno.model.JobRequestCreation;
import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.JobRequestRepository;
import com.exteso.bruno.repository.UserRepository;

@Service
public class JobRequestService {
    
    private final UserRepository userRepository;
    private final JobRequestRepository jobRequestRepository;

    @Autowired
    public JobRequestService(JobRequestRepository jobRequestRepository, UserRepository userRepository) {
        this.jobRequestRepository = jobRequestRepository;
        this.userRepository = userRepository;
    }
    
    public void create(JobRequestCreation jobRequest, UserIdentifier from, Date date) {
        jobRequestRepository.create(date, userRepository.getId(from.getProvider(), from.getUsername()), jobRequest.getAddress(), jobRequest.getUrgent(), 
                jobRequest.getFaultType(), jobRequest.getDescription(), jobRequest.getRequestType());
    }
    
    
    public List<JobRequest> findForUser(UserIdentifier user) {
        long userId = userRepository.getId(user.getProvider(), user.getUsername());
        return jobRequestRepository.findAllForUser(userId);
    }
    
    private Set<FailureType> requestTypeHandled(UserIdentifier from) {
        return EnumSet.allOf(FailureType.class); //FIXME
    }

    public List<JobRequest> findForServiceProvider(UserIdentifier from) {
        Set<String> handled = requestTypeHandled(from).stream().map(Enum::name).collect(Collectors.toSet());//FIXME;
        return jobRequestRepository.findAllWithType(handled);
    }
}
