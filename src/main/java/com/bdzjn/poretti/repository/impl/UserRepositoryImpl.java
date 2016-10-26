package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.UserRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class UserRepositoryImpl extends QueryDslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() {
        super(User.class);
    }

}
