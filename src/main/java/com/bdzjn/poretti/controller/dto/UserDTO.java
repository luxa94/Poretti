package com.bdzjn.poretti.controller.dto;


import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String name;

    private String imageUrl;

    private List<String> phoneNumbers = new ArrayList<>();

    private List<String> contactEmails = new ArrayList<>();

    @Override
    public String toString() {
        return "UserDTO{" +
                ", name='" + name + '\'' +
                ", imageId=" + imageUrl +
                ", phoneNumbers=" + phoneNumbers +
                ", contactEmails=" + contactEmails +
                '}';
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

}
