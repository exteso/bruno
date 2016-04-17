package com.exteso.bruno.model;

import java.util.Date;

import ch.digitalfondue.npjt.ConstructorAnnotationRowMapper.Column;

public class User {
    
    public enum UserType {
        CUSTOMER, SERVICE_PROVIDER, ADMIN
    }

    private final long id;
    private final String provider;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final UserType userType;
    private final UserType userRequestType;
    private final Date userRequestTypeDate;
    //
    private final String companyName;
    private final Integer fieldOfWork;
    private final String companyAddress;
    private final String companyPhoneNumber;
    private final String companyEmail;
    private final String vatIdentifier;
    private final String notes;
    //
    
    
    public User(@Column("id") long id, 
            @Column("provider") String provider, 
            @Column("username") String username, 
            @Column("first_name") String firstname, 
            @Column("last_name") String lastname, 
            @Column("email_address") String email,
            @Column("user_type") UserType userType,
            @Column("user_request_type") UserType userRequestType,
            @Column("user_request_type_date") Date userRequestTypeDate,
            @Column("company_name") String companyName,
            @Column("company_field_of_work") Integer fieldOfWork,
            @Column("company_address") String companyAddress,
            @Column("company_phone_number") String companyPhoneNumber,
            @Column("company_email") String companyEmail,
            @Column("company_vat_id") String vatIdentifier,
            @Column("company_notes") String notes) {
        this.id = id;
        this.provider = provider;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userType = userType;
        this.userRequestType = userRequestType;
        this.userRequestTypeDate = userRequestTypeDate;
        
        //
        this.companyName = companyName;
        this.fieldOfWork = fieldOfWork;
        this.companyAddress = companyAddress;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyEmail = companyEmail;
        this.vatIdentifier = vatIdentifier;
        this.notes = notes;
        //
    }


    public long getId() {
        return id;
    }


    public String getProvider() {
        return provider;
    }


    public String getUsername() {
        return username;
    }


    public String getFirstname() {
        return firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public String getEmail() {
        return email;
    }
    
    public UserType getUserType() {
        return userType;
    }


    public UserType getUserRequestType() {
        return userRequestType;
    }


    public Date getUserRequestTypeDate() {
        return userRequestTypeDate;
    }


    public String getCompanyName() {
        return companyName;
    }


    public Integer getFieldOfWork() {
        return fieldOfWork;
    }


    public String getCompanyAddress() {
        return companyAddress;
    }


    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }


    public String getCompanyEmail() {
        return companyEmail;
    }


    public String getVatIdentifier() {
        return vatIdentifier;
    }


    public String getNotes() {
        return notes;
    }
}
