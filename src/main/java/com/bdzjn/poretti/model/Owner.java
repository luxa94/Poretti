package com.bdzjn.poretti.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Owner {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @NotNull
    @OneToOne
    private Image image;

    @NotNull
    @OneToOne
    private Location location;

    @ElementCollection
    @CollectionTable(name = "PhoneNumber", joinColumns = @JoinColumn(name = "owner", referencedColumnName = "id"))
    @Column(name = "phoneNumber", nullable = false)
    private List<String> phoneNumbers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "Email", joinColumns = @JoinColumn(name = "owner", referencedColumnName = "id"))
    @Column(name = "email", nullable = false)
    private List<String> contactEmails = new ArrayList<>();

    @OneToMany(mappedBy = "advertiser")
    private List<Advertisement> advertisements = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<RealEstate> realEstates = new ArrayList<>();

    @OneToMany(mappedBy = "target")
    private List<OwnerReview> reviews = new ArrayList<>();

}
