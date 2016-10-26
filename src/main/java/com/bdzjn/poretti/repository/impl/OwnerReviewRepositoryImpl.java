package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.OwnerReview;
import com.bdzjn.poretti.repository.OwnerReviewRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class OwnerReviewRepositoryImpl extends QueryDslRepositorySupport implements OwnerReviewRepositoryCustom {

    public OwnerReviewRepositoryImpl() {
        super(OwnerReview.class);
    }

}
