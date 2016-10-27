package com.bdzjn.poretti.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class OwnerReview extends Review {

    @ManyToOne
    private Owner target;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReview)) return false;
        if (!super.equals(o)) return false;

        OwnerReview that = (OwnerReview) o;

        return getTarget().equals(that.getTarget());

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getTarget().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OwnerReview{" +
                "target=" + target +
                '}';
    }

    public Owner getTarget() {
        return target;
    }

    public void setTarget(Owner target) {
        this.target = target;
    }
}
