package com.bdzjn.poretti.model;

import javax.persistence.*;
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

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        if (!super.equals(o)) return false;

        Company company = (Company) o;

        return getPib().equals(company.getPib());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getPib().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "pib='" + pib + '\'' +
                "location=" + location +
                '}';
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
