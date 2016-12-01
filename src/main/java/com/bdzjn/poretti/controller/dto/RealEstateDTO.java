package com.bdzjn.poretti.controller.dto;

import com.bdzjn.poretti.model.Location;
import java.util.List;

public class RealEstateDTO {

    private long id;

    private String name;

    private double area;

    private String description;

    private Location location;

    private List<String> tehnicalEquipment;

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

    public List<String> getTehnicalEquipment() {
        return tehnicalEquipment;
    }

    public void setTehnicalEquipment(List<String> tehnicalEquipment) {
        this.tehnicalEquipment = tehnicalEquipment;
    }
}
