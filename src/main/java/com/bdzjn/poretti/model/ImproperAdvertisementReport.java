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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ImproperReportReason getReason() {
        return reason;
    }

    public void setReason(ImproperReportReason reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public Date getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Date editedOn) {
        this.editedOn = editedOn;
    }
}
