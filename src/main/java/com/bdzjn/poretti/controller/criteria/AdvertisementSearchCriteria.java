package com.bdzjn.poretti.controller.criteria;


import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.RealEstateType;

public class AdvertisementSearchCriteria {
    private final String realEstateName;
    private final Double areaFrom;
    private final Double areaTo;
    private final String city;
    private final String cityArea;
    private final String state;
    private final String street;
    private final Double latitude;
    private final Double longitude;
    private final RealEstateType realEstateType;
    private final String advertisementTitle;
    private final AdvertisementType advertisementType;
    private final Double priceFrom;
    private final Double priceTo;
    private final Currency currency;

    public AdvertisementSearchCriteria(String realEstateName, Double areaFrom, Double areaTo,
                                       String city, String cityArea, String state, String street, Double latitude,
                                       Double longitude, RealEstateType realEstateType, String advertisementTitle,
                                       AdvertisementType advertisementType, Double priceFrom, Double priceTo, Currency currency) {
        this.realEstateName = realEstateName;
        this.areaFrom = areaFrom;
        this.areaTo = areaTo;
        this.city = city;
        this.cityArea = cityArea;
        this.state = state;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
        this.realEstateType = realEstateType;
        this.advertisementTitle = advertisementTitle;
        this.advertisementType = advertisementType;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.currency = currency;
    }

    public String getRealEstateName() {
        return realEstateName;
    }

    public Double getAreaFrom() {
        return areaFrom;
    }

    public Double getAreaTo() {
        return areaTo;
    }

    public String getCity() {
        return city;
    }

    public String getCityArea() {
        return cityArea;
    }

    public String getState() {
        return state;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public RealEstateType getRealEstateType() {
        return realEstateType;
    }

    public String getAdvertisementTitle() {
        return advertisementTitle;
    }

    public AdvertisementType getAdvertisementType() {
        return advertisementType;
    }

    public Double getPriceFrom() {
        return priceFrom;
    }

    public Double getPriceTo() {
        return priceTo;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getStreet() {
        return street;
    }
}
