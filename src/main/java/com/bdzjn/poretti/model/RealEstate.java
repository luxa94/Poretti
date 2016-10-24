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

}
