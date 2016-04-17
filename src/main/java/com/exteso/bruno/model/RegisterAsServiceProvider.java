package com.exteso.bruno.model;

import java.util.Map;

import org.springframework.util.Assert;

public class RegisterAsServiceProvider {

    private String companyName;
    private Map<CompanyType, Boolean> fieldOfWorks;
    private String address;
    private String phoneNumber;
    private String contactEmail;
    private String vatNumber;
    private String notes;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Map<CompanyType, Boolean> getFieldOfWorks() {
        return fieldOfWorks;
    }

    public void setFieldOfWorks(Map<CompanyType, Boolean> fieldOfWorks) {
        this.fieldOfWorks = fieldOfWorks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public int getFieldOfWork() {
        Assert.isTrue(fieldOfWorks != null, "fieldOfWorks cannot be null");
        
        //
        int r = fieldOfWorks.entrySet().stream()//
                .filter(kv -> kv.getValue())//
                .map(kv -> kv.getKey().position())//
                .reduce(0, (total, v) -> total | v);
        
        Assert.isTrue(r != 0, "At least one must be selected");
        return r;
    }
}
