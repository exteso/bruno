package com.exteso.bruno.model;

import java.util.Date;

import ch.digitalfondue.npjt.ConstructorAnnotationRowMapper.Column;

public class JobRequest {
    
    private final Long id;
    private final Date creationTime;
    private final boolean urgent;
    private final FailureType failureType;
    private final String description;
    private final RequestType requestType;

    public JobRequest(@Column("id") Long id,//
            @Column("creation_time") Date creationTime,//
            @Column("creation_user_fk") Long creationUser,
            @Column("address") String address,//
            @Column("urgent") boolean urgent,
            @Column("fault_type") FailureType failureType,
            @Column("description") String description,
            @Column("request_type") RequestType requestType) {
        this.id = id;
        this.creationTime = creationTime;
        this.urgent = urgent;
        this.failureType = failureType;
        this.description = description;
        this.requestType = requestType;
    }

    public Long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public FailureType getFailureType() {
        return failureType;
    }

    public String getDescription() {
        return description;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
