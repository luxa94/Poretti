package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.InvalidRangeException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.controller.exception.UnprocessableEntityException;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.OwnerReview;
import com.bdzjn.poretti.model.User;

public interface OwnerReviewService {

    /**
     * Creates review for specified target user.
     *
     * @param reviewDTO Object which contains data for new review.
     * @param target Target user of this review.
     * @param author Author of this review.
     * @return
     */
    OwnerReview create(ReviewDTO reviewDTO, Owner target, User author);

    /**
     * Deletes review with the given id.
     * Delete will succeed only if this user is the author.
     * Otherwise {@link NotFoundException} will be thrown.
     *
     * @param id Id of advertisement to be deleted.
     * @param user Currently logged in user.
     */
    void delete(long id, User user);

}
