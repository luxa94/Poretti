package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.repository.AuthorizationRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class AuthorizationRepositoryImpl extends QueryDslRepositorySupport implements AuthorizationRepositoryCustom {

    public AuthorizationRepositoryImpl() {
        super(Authorization.class);
    }
}
