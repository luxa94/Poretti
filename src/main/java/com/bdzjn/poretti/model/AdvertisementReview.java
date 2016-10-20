package com.bdzjn.poretti.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class AdvertisementReview extends Review {

    @NotNull
    @ManyToOne
    private Advertisement target;

}

