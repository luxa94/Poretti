package com.bdzjn.poretti.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OwnerReview extends Review {

    @ManyToOne
    private Owner target;

}
