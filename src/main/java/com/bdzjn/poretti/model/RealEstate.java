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
    @OneToOne
    private Location location;

    @NotNull
    @OneToOne
    private Image image;

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
    @OneToMany(mappedBy = "realEstate")
    private List<Advertisement> advertisements = new ArrayList<>();

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
