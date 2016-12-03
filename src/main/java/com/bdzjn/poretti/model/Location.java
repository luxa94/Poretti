package com.bdzjn.poretti.model;

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

    @NotNull
    private double latitude = 0;

    @NotNull
    private double longitude = 0;

    @NotNull
    private boolean hasLatLong = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (Double.compare(location.getLatitude(), getLatitude()) != 0) return false;
        if (Double.compare(location.getLongitude(), getLongitude()) != 0) return false;
        if (!getCity().equals(location.getCity())) return false;
        if (getCityArea() != null ? !getCityArea().equals(location.getCityArea()) : location.getCityArea() != null)
            return false;
        if (getStreet() != null ? !getStreet().equals(location.getStreet()) : location.getStreet() != null)
            return false;
        if (getStreetNumber() != null ? !getStreetNumber().equals(location.getStreetNumber()) : location.getStreetNumber() != null)
            return false;
        if (getState() != null ? !getState().equals(location.getState()) : location.getState() != null) return false;
        return getZipCode() != null ? getZipCode().equals(location.getZipCode()) : location.getZipCode() == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getCity().hashCode();
        result = 31 * result + (getCityArea() != null ? getCityArea().hashCode() : 0);
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + (getStreetNumber() != null ? getStreetNumber().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getZipCode() != null ? getZipCode().hashCode() : 0);
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", cityArea='" + cityArea + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", hasLatLong=" + hasLatLong +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityArea() {
        return cityArea;
    }

    public void setCityArea(String cityArea) {
        this.cityArea = cityArea;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isHasLatLong() {
        return hasLatLong;
    }

    public void setHasLatLong(boolean hasLatLong) {
        this.hasLatLong = hasLatLong;
    }
}
