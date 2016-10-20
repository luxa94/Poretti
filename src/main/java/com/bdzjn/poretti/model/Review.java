package com.bdzjn.poretti.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Review {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private int rating;

    @NotNull
    private String comment;

    @NotNull
    private Date editedOn;

    @NotNull
    @ManyToOne
    private User author;

}
