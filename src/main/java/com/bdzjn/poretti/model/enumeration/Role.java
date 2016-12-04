package com.bdzjn.poretti.model.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Role {
    // If you change this, update sql files as well!
    USER(new Permission[]{Permission.CREATE_ADVERTISEMENT,
            Permission.EDIT_ADVERTISEMENT,
            Permission.DELETE_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.DELETE_REVIEW,
            Permission.CREATE_ADVERTISEMENT_REPORT,
            Permission.APPROVE_COMPANY_USER,
            Permission.REMOVE_USER_FROM_COMPANY,
            Permission.JOIN_COMPANY,
            Permission.LEAVE_COMPANY,
            Permission.EDIT_COMPANY}),

    // If you change this, update sql files as well!
    VERIFIER(new Permission[]{
            Permission.CREATE_REVIEW,
            Permission.CREATE_ADVERTISEMENT_REPORT,
            Permission.CHANGE_ADVERTISEMENT_STATUS}),

    // If you change this, update sql files as well!
    SYSTEM_ADMIN(new Permission[]{Permission.EDIT_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.CREATE_ADVERTISEMENT_REPORT,
            Permission.EDIT_COMPANY,
            Permission.CREATE_COMPANY,
            Permission.CREATE_SYSTEM_ADMIN,
            Permission.CREATE_VERIFIER,
            Permission.BAN_USER,
            Permission.DELETE_REVIEW});

    private final Permission[] permissions;

    Role(Permission[] permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return new ArrayList<>(Arrays.asList(permissions));
    }

}
