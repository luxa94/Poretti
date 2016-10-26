package com.bdzjn.poretti.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OwnerReview extends Review {

    @ManyToOne
    private Owner target;

    public Owner getTarget() {
        return target;
    }

    public void setTarget(Owner target) {
        this.target = target;
    }
}
