package com.bdzjn.poretti.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company extends Owner {

    @NotNull
    @Column(unique = true, nullable = false)
    private String pib;

    @OneToMany(mappedBy = "company")
    private List<Membership> memberships = new ArrayList<>();

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

}
