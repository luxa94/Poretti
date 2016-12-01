package com.bdzjn.poretti.controller.dto;

import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;

import java.util.Date;

public class AdvertisementDTO {

    private long id;

    private String title;

    private Date endsOn;

    private AdvertisementType type;

    private double price;

    private Currency currency;

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

    public Date getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(Date endsOn) {
        this.endsOn = endsOn;
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
}
