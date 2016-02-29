package com.exteso.bruno.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

import com.exteso.bruno.model.FailureType;
import com.exteso.bruno.model.JobRequest;
import com.exteso.bruno.model.RequestType;

@QueryRepository
public interface JobRequestRepository {

    @Query("insert into b_job_request(creation_time, creation_user_fk, address, urgent, fault_type, description, request_type) "
            + " values (:creation_time, :creation_user_fk, :address, :urgent, :fault_type, :description, :request_type)")
    int create(@Bind("creation_time") Date creationTime, @Bind("creation_user_fk") Long createUserId, @Bind("address") String address, 
                @Bind("urgent") boolean urgent, @Bind("fault_type") FailureType faultType, 
                @Bind("description") String description, @Bind("request_type") RequestType requestType);
    
    
    
    @Query("select * from b_job_request where creation_user_fk = :userId order by creation_time DESC")
    List<JobRequest> findAllForUser(@Bind("userId") Long userId);


    @Query("select * from b_job_request where fault_type in (:handled) order by creation_time DESC")
    List<JobRequest> findAllWithType(@Bind("handled") Set<String> handled);
    
    @Query("select * from b_job_request where id = :id")
    JobRequest findById(@Bind("id") long id);
}
