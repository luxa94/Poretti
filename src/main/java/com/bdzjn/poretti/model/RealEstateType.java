package com.bdzjn.poretti.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RealEstateType {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToOne
    private RealEstateType superType;

    @OneToMany(mappedBy = "superType")
    private List<RealEstateType> subTypes = new ArrayList<>();

}
