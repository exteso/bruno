package com.exteso.bruno.model;

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
    
    
    public User(@Column("id") long id, 
            @Column("provider") String provider, 
            @Column("username") String username, 
            @Column("first_name") String firstname, 
            @Column("last_name") String lastname, 
            @Column("email_address") String email,
            @Column("user_type") UserType userType) {
        this.id = id;
        this.provider = provider;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userType = userType;
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
}
