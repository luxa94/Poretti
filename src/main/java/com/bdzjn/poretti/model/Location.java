package com.bdzjn.poretti.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String city;
    private String cityArea;
    private String street;
    private String streetNumber;
    private String state;
    private String zipCode;

    @Column(nullable = true)
    private double latitude;

    @Column(nullable = true)
    private double longitude;

    @Column(nullable = true)
    private boolean hasLatLong;

}
