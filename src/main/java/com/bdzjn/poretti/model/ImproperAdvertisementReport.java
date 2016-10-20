package com.bdzjn.poretti.model;

import com.bdzjn.poretti.model.enumeration.ImproperReportReason;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class ImproperAdvertisementReport {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ImproperReportReason reason;

    @NotNull
    private String description;

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    @ManyToOne
    private Advertisement advertisement;

    @NotNull
    private Date editedOn;

}
