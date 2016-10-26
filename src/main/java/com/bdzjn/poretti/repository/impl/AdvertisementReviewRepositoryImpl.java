package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.AdvertisementReview;
import com.bdzjn.poretti.repository.AdvertisementReviewRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class AdvertisementReviewRepositoryImpl extends QueryDslRepositorySupport implements AdvertisementReviewRepositoryCustom {

    public AdvertisementReviewRepositoryImpl() {
        super(AdvertisementReview.class);
    }

}
