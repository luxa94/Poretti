package com.bdzjn.poretti.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Membership {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private Company company;

    @NotNull
    @ManyToOne
    private User member;

    @NotNull
    private boolean confirmed;

    @ManyToOne
    private User approvedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }
}
