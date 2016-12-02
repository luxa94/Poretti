package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.OwnerReview;
import com.bdzjn.poretti.model.User;

public interface OwnerReviewService {
    OwnerReview create(ReviewDTO reviewDTO, Owner target, User author);
}
