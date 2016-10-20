package com.bdzjn.poretti.model;

import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String title;

    @NotNull
    private Date announcedOn;

    @NotNull
    private Date editedOn;

    @NotNull
    private Date endsOn;

    @NotNull
    @ManyToOne
    private Owner advertiser;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertisementStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertisementType type;

    @NotNull
    private double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @ManyToOne
    private RealEstate realEstate;

    @ManyToOne
    private User verifier;

    @OneToMany(mappedBy = "advertisement")
    private List<ImproperAdvertisementReport> reports = new ArrayList<>();

    @OneToMany(mappedBy = "target")
    private List<AdvertisementReview> reviews = new ArrayList<>();

}
