package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.InvalidRangeException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.UnprocessableEntityException;
import com.bdzjn.poretti.model.AdvertisementReview;
import com.bdzjn.poretti.model.User;

public interface AdvertisementReviewService {

    /**
     * Create will succeed if author is not advertiser of advertisement with the given id, also if rating is
     * between 1-10, and status of advertisement if active.
     * In case when author is advertiser {@link ForbiddenException}
     * In case when rating is invalid {@link InvalidRangeException}
     * In case when status of advertisement is not active {@link UnprocessableEntityException}
     * @param reviewDTO Object which contains data for new review
     * @param advertisementId Id of advertisement
     * @param author Author of this review
     * @return
     */
    AdvertisementReview create(ReviewDTO reviewDTO, long advertisementId, User author);

    /**
     * Delete will succeed nnly if this user is the advertiser.
     * Otherwise {@link NotFoundException} will be thrown.
     *
     * @param id Id of advertisement to be delete.d
     * @param user Currently logged in user.
     */
    void delete(long id, User user);

}
