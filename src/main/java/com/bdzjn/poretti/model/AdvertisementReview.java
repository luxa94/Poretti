package com.bdzjn.poretti.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class AdvertisementReview extends Review {

    @NotNull
    @ManyToOne
    private Advertisement target;

    public Advertisement getTarget() {
        return target;
    }

    public void setTarget(Advertisement target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertisementReview)) return false;
        if (!super.equals(o)) return false;

        AdvertisementReview that = (AdvertisementReview) o;

        return getTarget() != null ? getTarget().equals(that.getTarget()) : that.getTarget() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTarget() != null ? getTarget().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdvertisementReview{" +
                "target=" + target +
                '}';
    }
}

