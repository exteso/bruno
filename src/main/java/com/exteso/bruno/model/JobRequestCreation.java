package com.exteso.bruno.model;

import java.util.Optional;

public class JobRequestCreation {
    
    private String address;
    private Boolean urgent;
    private FailureType faultType;
    private String description;
    private RequestType requestType;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getUrgent() {
        return Optional.ofNullable(urgent).orElse(false);
    }

    public void setUrgent(Boolean urgent) {
        this.urgent = urgent;
    }

    public FailureType getFaultType() {
        return faultType;
    }

    public void setFaultType(FailureType faultType) {
        this.faultType = faultType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

}