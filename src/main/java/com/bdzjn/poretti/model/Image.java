package com.bdzjn.poretti.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    private String url;

}
