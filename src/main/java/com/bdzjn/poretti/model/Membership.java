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


}
