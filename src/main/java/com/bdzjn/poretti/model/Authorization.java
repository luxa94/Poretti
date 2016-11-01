package com.bdzjn.poretti.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Authorization {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @Column(unique = true)
    private String token;

    @PrePersist
    private void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authorization)) return false;

        Authorization that = (Authorization) o;

        if (!getUser().equals(that.getUser())) return false;
        return getToken().equals(that.getToken());

    }

    @Override
    public int hashCode() {
        int result = getUser().hashCode();
        result = 31 * result + getToken().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "id=" + id +
                ", user=" + user +
                ", token='" + token + '\'' +
                '}';
    }

}
