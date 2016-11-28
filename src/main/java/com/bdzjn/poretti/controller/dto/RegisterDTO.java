package com.bdzjn.poretti.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class RegisterDTO {

    private String username;

    private String email;

    private String password;

    private String name;

    private String imageUrl;

    private List<String> phoneNumbers = new ArrayList<>();

    private List<String> contactEmails = new ArrayList<>();

    private long companyId;

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", imageId=" + imageUrl +
                ", phoneNumbers=" + phoneNumbers +
                ", contactEmails=" + contactEmails +
                ", companyId=" + companyId +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getContactEmails() {
        return contactEmails;
    }

    public void setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "/images/defaultUser.jpg";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
