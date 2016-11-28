package com.bdzjn.poretti.controller.dto;


import com.bdzjn.poretti.model.enumeration.Permission;
import com.bdzjn.poretti.model.enumeration.Role;

import java.util.List;

public class AuthorizationDTO {

    private long id;

    private boolean verified;

    private String token;

    private Role role;

    private List<Permission> permissions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
