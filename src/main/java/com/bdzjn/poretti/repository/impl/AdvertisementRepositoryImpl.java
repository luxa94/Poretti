package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.repository.AdvertisementRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class AdvertisementRepositoryImpl extends QueryDslRepositorySupport implements AdvertisementRepositoryCustom {

    public AdvertisementRepositoryImpl() {
        super(Advertisement.class);
    }

}
