package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.model.AdvertisementReview;
import com.bdzjn.poretti.model.User;

public interface AdvertisementReviewService {

    AdvertisementReview create(ReviewDTO reviewDTO, long advertisementId, User author);

    void delete(long id, User user);

}
