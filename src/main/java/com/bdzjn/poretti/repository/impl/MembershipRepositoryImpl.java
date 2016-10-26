package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.repository.MembershipRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;


public class MembershipRepositoryImpl extends QueryDslRepositorySupport implements MembershipRepositoryCustom {

    public MembershipRepositoryImpl() {
        super(Membership.class);
    }
}