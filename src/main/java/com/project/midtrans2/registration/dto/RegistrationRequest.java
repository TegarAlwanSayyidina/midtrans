package com.project.midtrans2.registration.dto;

public class RegistrationRequest {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String businessLegality;

    // Constructor default
    public RegistrationRequest() {}

    // Constructor dengan parameter
    public RegistrationRequest(String fullName, String email, String password, String phoneNumber, String businessLegality) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.businessLegality = businessLegality;
    }

    // Getter dan Setter untuk fullName
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter dan Setter untuk email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter dan Setter untuk password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter dan Setter untuk phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter dan Setter untuk businessLegality
    public String getBusinessLegality() {
        return businessLegality;
    }

    public void setBusinessLegality(String businessLegality) {
        this.businessLegality = businessLegality;
    }
}