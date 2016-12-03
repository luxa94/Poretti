package com.bdzjn.poretti.model;

import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RealEstate {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @NotNull
    private double area;

    @NotNull
    private String description;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @NotNull
    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RealEstateType type;

    @NotNull
    @ManyToOne
    private Owner owner;

    @ElementCollection
    @CollectionTable(name = "TechnicalEquipment", joinColumns = @JoinColumn(name = "realEstate", referencedColumnName = "id"))
    @Column(name = "name")
    private List<String> technicalEquipment = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.REMOVE)
    private List<Advertisement> advertisements = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstate)) return false;

        RealEstate that = (RealEstate) o;

        if (!getLocation().equals(that.getLocation())) return false;
        if (getType() != that.getType()) return false;
        return getOwner() != null ? getOwner().equals(that.getOwner()) : that.getOwner() == null;

    }

    @Override
    public int hashCode() {
        int result = getLocation().hashCode();
        result = 31 * result + getType().hashCode();
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", area=" + area +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", type=" + type +
                ", owner=" + owner +
                '}';
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RealEstateType getType() {
        return type;
    }

    public void setType(RealEstateType type) {
        this.type = type;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<String> getTechnicalEquipment() {
        return technicalEquipment;
    }

    public void setTechnicalEquipment(List<String> technicalEquipment) {
        this.technicalEquipment = technicalEquipment;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
