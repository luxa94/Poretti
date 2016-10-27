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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImproperAdvertisementReport)) return false;

        ImproperAdvertisementReport that = (ImproperAdvertisementReport) o;

        if (getReason() != that.getReason()) return false;
        if (!getAuthor().equals(that.getAuthor())) return false;
        if (!getAdvertisement().equals(that.getAdvertisement())) return false;
        return getEditedOn().equals(that.getEditedOn());

    }

    @Override
    public int hashCode() {
        int result = getReason().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getAdvertisement().hashCode();
        result = 31 * result + getEditedOn().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ImproperAdvertisementReport{" +
                "id=" + id +
                ", reason=" + reason +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", advertisement=" + advertisement +
                ", editedOn=" + editedOn +
                '}';
    }

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
