package com.bdzjn.poretti.model;

import com.bdzjn.poretti.model.enumeration.Permission;
import com.bdzjn.poretti.model.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends Owner {

    @NotNull
    @Column(unique = true)
    private String username;

    @JsonIgnore
    @NotNull
    private String password;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private boolean registrationConfirmed;

    @OneToMany(mappedBy = "member")
    private List<Membership> memberships = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "UserPermission", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public void setRegistrationConfirmed(boolean registrationConfirmed) {
        this.registrationConfirmed = registrationConfirmed;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
