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
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner)) return false;

        Owner owner = (Owner) o;

        if (!getName().equals(owner.getName())) return false;
        return getLocation().equals(owner.getLocation());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getLocation().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", location=" + location +
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getContactEmails() {
        return contactEmails;
    }

    public void setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public List<RealEstate> getRealEstates() {
        return realEstates;
    }

    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
    }

    public List<OwnerReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<OwnerReview> reviews) {
        this.reviews = reviews;
    }
}
