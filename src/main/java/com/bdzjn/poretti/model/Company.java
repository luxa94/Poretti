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
    private List<User> employees = new ArrayList<>();

}