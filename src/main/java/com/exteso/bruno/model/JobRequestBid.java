package com.exteso.bruno.model;

import java.util.Date;

import ch.digitalfondue.npjt.ConstructorAnnotationRowMapper.Column;

public class JobRequestBid {
    
    
    private final long requestId;
    private final long userId;
    private final Long price;
    private final Date selectedDate;
    private final Boolean accepted;
    
    public JobRequestBid(
            @Column("job_request_fk") long requestId, 
            @Column("user_fk") long userId, 
            @Column("price") Long price, 
            @Column("selected_date") Date selectedDate, 
            @Column("accepted") Boolean accepted) {
        this.requestId = requestId;
        this.userId = userId;
        this.price = price;
        this.selectedDate = selectedDate;
        this.accepted = accepted;
    }
    
    
    public long getRequestId() {
        return requestId;
    }
    public long getUserId() {
        return userId;
    }
    public Long getPrice() {
        return price;
    }
    public Date getSelectedDate() {
        return selectedDate;
    }
    public Boolean getAccepted() {
        return accepted;
    }
}
