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

    @ManyToOne
    private Company company;

    @ElementCollection
    @CollectionTable(name = "UserPermission", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"))
    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Permission> permissions = new ArrayList<>();

}
