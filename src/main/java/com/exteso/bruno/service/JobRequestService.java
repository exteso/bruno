package com.exteso.bruno.service;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exteso.bruno.model.FailureType;
import com.exteso.bruno.model.JobRequest;
import com.exteso.bruno.model.JobRequestBid;
import com.exteso.bruno.model.JobRequestCreation;
import com.exteso.bruno.model.JobRequestBidModification;
import com.exteso.bruno.model.RequestState;
import com.exteso.bruno.model.UserIdentifier;
import com.exteso.bruno.repository.JobRequestBidRepository;
import com.exteso.bruno.repository.JobRequestRepository;
import com.exteso.bruno.repository.UserRepository;

@Service
@Transactional
public class JobRequestService {
    
    private final UserRepository userRepository;
    private final JobRequestRepository jobRequestRepository;
    private final JobRequestBidRepository jobRequestBidRepository;

    @Autowired
    public JobRequestService(JobRequestRepository jobRequestRepository, JobRequestBidRepository jobRequestBidRepository, UserRepository userRepository) {
        this.jobRequestRepository = jobRequestRepository;
        this.jobRequestBidRepository = jobRequestBidRepository;
        this.userRepository = userRepository;
    }
    
    public void create(JobRequestCreation jobRequest, UserIdentifier from, Date date) {
        jobRequestRepository.create(date, userRepository.getId(from.getProvider(), from.getUsername()), jobRequest.getAddress(), jobRequest.getUrgent(), 
                jobRequest.getFaultType(), jobRequest.getDescription(), jobRequest.getRequestType(), RequestState.OPEN);
    }
    
    
    public List<JobRequest> findForUser(UserIdentifier user) {
        long userId = userRepository.getId(user.getProvider(), user.getUsername());
        return jobRequestRepository.findAllForUser(userId);
    }
    
    private Set<FailureType> requestTypeHandled(UserIdentifier from) {
        return EnumSet.allOf(FailureType.class); //FIXME
    }

    public List<JobRequest> findOpenForServiceProvider(UserIdentifier from) {
        Set<String> handled = requestTypeHandled(from).stream().map(Enum::name).collect(Collectors.toSet());//FIXME;
        return jobRequestRepository.findAllWithTypeAndState(handled, RequestState.OPEN);
    }
    
    public JobRequest findById(long id) {
        return jobRequestRepository.findById(id);
    }

    public void createOrUpdateBid(long requestId, JobRequestBidModification bid, UserIdentifier from) {
        Long userId = userRepository.getId(from.getProvider(), from.getUsername());
        if(jobRequestBidRepository.count(requestId, userId) == 1) {
            jobRequestBidRepository.update(requestId, userId, bid.getCost(), bid.getDate());
        } else {
            jobRequestBidRepository.create(requestId, userId, bid.getCost(), bid.getDate());
        }
    }

    public Optional<JobRequestBid> findBy(long id, UserIdentifier from) {
        return jobRequestBidRepository.findBy(id, userRepository.getId(from.getProvider(), from.getUsername()));
    }
}
