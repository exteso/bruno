package com.exteso.bruno.model;

import java.util.Date;

import ch.digitalfondue.npjt.ConstructorAnnotationRowMapper.Column;

public class JobRequest {
    
    private final Long id;
    private final Date creationTime;
    private final long creationUser;
    private final boolean urgent;
    private final FailureType failureType;
    private final String description;
    private final RequestType requestType;
    private final String address;
    private final RequestState state;
    

    public JobRequest(@Column("id") long id,//
            @Column("creation_time") Date creationTime,//
            @Column("creation_user_fk") long creationUser,
            @Column("address") String address,//
            @Column("urgent") boolean urgent,
            @Column("fault_type") FailureType failureType,
            @Column("description") String description,
            @Column("request_type") RequestType requestType,
            @Column("request_state") RequestState state) {
        this.id = id;
        this.creationTime = creationTime;
        this.creationUser = creationUser;
        this.address = address;
        this.urgent = urgent;
        this.failureType = failureType;
        this.description = description;
        this.requestType = requestType;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getAddress() {
        return address;
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

    public RequestState getState() {
        return state;
    }

    public long getCreationUser() {
        return creationUser;
    }
}
