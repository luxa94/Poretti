package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.repository.RealEstateRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class RealEstateRepositoryImpl extends QueryDslRepositorySupport implements RealEstateRepositoryCustom {

    public RealEstateRepositoryImpl() {
        super(RealEstate.class);
    }

}
