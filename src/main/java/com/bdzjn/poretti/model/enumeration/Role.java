package com.bdzjn.poretti.model.enumeration;


import java.util.Arrays;
import java.util.List;

public enum Role {
    USER(new Permission[]{Permission.CREATE_ADVERTISEMENT,
            Permission.EDIT_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.EDIT_REVIEW,
            Permission.REPORT_ADVERTISEMENT}),

    COMPANY_USER(new Permission[]{Permission.CREATE_ADVERTISEMENT,
            Permission.EDIT_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.EDIT_REVIEW,
            Permission.REPORT_ADVERTISEMENT,
            Permission.CREATE_COMPANY_ADVERTISEMENT,
            Permission.EDIT_COMPANY_ADVERTISEMENT}),

    COMPANY_ADMIN(new Permission[]{Permission.CREATE_ADVERTISEMENT,
            Permission.EDIT_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.EDIT_REVIEW,
            Permission.REPORT_ADVERTISEMENT,
            Permission.CREATE_COMPANY_ADVERTISEMENT,
            Permission.EDIT_COMPANY_ADVERTISEMENT,
            Permission.APPROVE_COMPANY_USER,
            Permission.REMOVE_USER_FROM_COMPANY,
            Permission.EDIT_COMPANY}),

    VERIFIER(new Permission[]{Permission.CREATE_ADVERTISEMENT,
            Permission.EDIT_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.EDIT_REVIEW,
            Permission.REPORT_ADVERTISEMENT,
            Permission.VERIFY_ADVERTISEMENT}),

    SYSTEM_ADMIN(new Permission[]{Permission.CREATE_ADVERTISEMENT,
            Permission.EDIT_ADVERTISEMENT,
            Permission.CREATE_REVIEW,
            Permission.EDIT_REVIEW,
            Permission.REPORT_ADVERTISEMENT,
            Permission.CREATE_COMPANY_ADVERTISEMENT,
            Permission.EDIT_COMPANY_ADVERTISEMENT,
            Permission.APPROVE_COMPANY_USER,
            Permission.REMOVE_USER_FROM_COMPANY,
            Permission.EDIT_COMPANY,
            Permission.VERIFY_ADVERTISEMENT,
            Permission.CREATE_COMPANY,
            Permission.CREATE_COMPANY_ADMIN,
            Permission.CREATE_VERIFIER,
            Permission.BAN_USER,
            Permission.DELETE_REVIEW});

    private final Permission[] permissions;

    Role(Permission[] permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return Arrays.asList(permissions);
    }

}
