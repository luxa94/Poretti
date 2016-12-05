package com.bdzjn.poretti.model;

import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String title;

    @NotNull
    private Date announcedOn;

    @NotNull
    private Date editedOn;

    @NotNull
    private Date endsOn;

    @NotNull
    @ManyToOne
    private Owner advertiser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertisementStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertisementType type;

    @NotNull
    private double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @ManyToOne
    private RealEstate realEstate;

    @JsonIgnore
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.REMOVE)
    private List<ImproperAdvertisementReport> reports = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "target", cascade = CascadeType.REMOVE)
    private List<AdvertisementReview> reviews = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;

        Advertisement that = (Advertisement) o;

        if (!getAnnouncedOn().equals(that.getAnnouncedOn())) return false;
        if (!getAdvertiser().equals(that.getAdvertiser())) return false;
        if (getType() != that.getType()) return false;
        return getRealEstate().equals(that.getRealEstate());

    }

    @Override
    public int hashCode() {
        int result = getAnnouncedOn().hashCode();
        result = 31 * result + getAdvertiser().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + getRealEstate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", announcedOn=" + announcedOn +
                ", editedOn=" + editedOn +
                ", endsOn=" + endsOn +
                ", advertiser=" + advertiser +
                ", status=" + status +
                ", type=" + type +
                ", price=" + price +
                ", currency=" + currency +
                ", realEstate=" + realEstate +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getAnnouncedOn() {
        return announcedOn;
    }

    public void setAnnouncedOn(Date announcedOn) {
        this.announcedOn = announcedOn;
    }

    public Date getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Date editedOn) {
        this.editedOn = editedOn;
    }

    public Date getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(Date endsOn) {
        this.endsOn = endsOn;
    }

    public Owner getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Owner advertiser) {
        this.advertiser = advertiser;
    }

    public AdvertisementStatus getStatus() {
        return status;
    }

    public void setStatus(AdvertisementStatus status) {
        this.status = status;
    }

    public AdvertisementType getType() {
        return type;
    }

    public void setType(AdvertisementType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public RealEstate getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
    }

    public List<ImproperAdvertisementReport> getReports() {
        return reports;
    }

    public void setReports(List<ImproperAdvertisementReport> reports) {
        this.reports = reports;
    }

    public List<AdvertisementReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<AdvertisementReview> reviews) {
        this.reviews = reviews;
    }
}
