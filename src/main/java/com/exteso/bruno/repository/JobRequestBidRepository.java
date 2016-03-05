package com.exteso.bruno.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.exteso.bruno.model.JobRequestBid;

import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

@QueryRepository
public interface JobRequestBidRepository {

    @Query("select count(*) from b_job_request_bid where job_request_fk = :requestId and user_fk = :userId")
    int count(@Bind("requestId") long requestId, @Bind("userId") long userId);
    
    @Query("insert into b_job_request_bid(job_request_fk, user_fk, price, selected_date) values (:requestId, :userId, :price, :date)")
    int create(@Bind("requestId") long requestId, @Bind("userId") long userId, @Bind("price") Long price, @Bind("date") Date date);

    @Query("update b_job_request_bid set price = :price, selected_date = :date where job_request_fk = :requestId and user_fk = :userId")
    int update(@Bind("requestId") long requestId, @Bind("userId") long userId, @Bind("price") Long price, @Bind("date") Date date);

    @Query("select * from b_job_request_bid where job_request_fk = :requestId and user_fk = :userId")
    Optional<JobRequestBid> findBy(@Bind("requestId") long requestId, @Bind("userId") long userId);

    @Query("select * from b_job_request_bid where job_request_fk = :requestId")
    List<JobRequestBid> findAll(@Bind("requestId") long requestId);

    
    @Query("update b_job_request_bid set accepted = (job_request_fk = :requestId and user_fk = :userId) where job_request_fk = :requestId")
    int accept(@Bind("requestId") long id, @Bind("userId") long userId);
    
}
