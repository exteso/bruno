package com.exteso.bruno.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

import com.exteso.bruno.model.FailureType;
import com.exteso.bruno.model.JobRequest;
import com.exteso.bruno.model.RequestState;
import com.exteso.bruno.model.RequestType;

@QueryRepository
public interface JobRequestRepository {

    @Query("insert into b_job_request(creation_time, creation_user_fk, address, urgent, fault_type, description, request_type, request_state) "
            + " values (:creation_time, :creation_user_fk, :address, :urgent, :fault_type, :description, :request_type, :request_state)")
    int create(@Bind("creation_time") Date creationTime, @Bind("creation_user_fk") Long createUserId, @Bind("address") String address, 
                @Bind("urgent") boolean urgent, @Bind("fault_type") FailureType faultType, 
                @Bind("description") String description, 
                @Bind("request_type") RequestType requestType,
                @Bind("request_state") RequestState requestState);
    
    
    
    @Query("select * from b_job_request where creation_user_fk = :userId order by creation_time DESC")
    List<JobRequest> findAllForUser(@Bind("userId") Long userId);


    @Query("select * from b_job_request where request_state = :state and fault_type in (:handled) order by creation_time DESC")
    List<JobRequest> findAllWithTypeAndState(@Bind("handled") Set<String> handled, @Bind("state") RequestState state);
    
    @Query("select * from b_job_request where id = :id")
    JobRequest findById(@Bind("id") long id);
    
    @Query("update b_job_request set request_state = :request_state where id = :id")
    int updateState(@Bind("id") long id, @Bind("request_state") RequestState requestState);


    @Query("select b_job_request.* from b_job_request "
            + " inner join b_job_request_bid on b_job_request.id = job_request_fk and b_job_request_bid.user_fk  = :userId "
            + " where request_state = :state and accepted = true order by creation_time DESC")
    List<JobRequest> findAllWithStateAssignedToUser(@Bind("state") RequestState assigned, @Bind("userId") Long userId);
}
