package com.bdzjn.poretti.controller.dto;

import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.enumeration.RealEstateType;

import java.util.List;

public class RealEstateDTO {

    private long id;

    private String name;

    private double area;

    private String description;

    private Location location;

    private List<String> technicalEquipment;

    private String imageUrl;

    private RealEstateType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getTechnicalEquipment() {
        return technicalEquipment;
    }

    public void setTechnicalEquipment(List<String> technicalEquipment) {
        this.technicalEquipment = technicalEquipment;
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "/images/defaultUser.jpg";
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RealEstateType getRealEstateType() {
        return type;
    }

    public void setRealEstateType(RealEstateType realEstateType) {
        this.type = realEstateType;
    }
}
