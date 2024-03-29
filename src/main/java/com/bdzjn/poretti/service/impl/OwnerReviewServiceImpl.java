package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.OwnerReview;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.OwnerReviewRepository;
import com.bdzjn.poretti.service.OwnerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OwnerReviewServiceImpl implements OwnerReviewService {

    private final OwnerReviewRepository ownerReviewRepository;

    @Autowired
    public OwnerReviewServiceImpl(OwnerReviewRepository ownerReviewRepository) {
        this.ownerReviewRepository = ownerReviewRepository;
    }

    @Override
    public OwnerReview create(ReviewDTO reviewDTO, Owner target, User author) {
        final OwnerReview ownerReview = new OwnerReview();
        ownerReview.setTarget(target);
        ownerReview.setAuthor(author);
        ownerReview.setComment(reviewDTO.getComment());
        ownerReview.setRating(reviewDTO.getRating());
        ownerReview.setEditedOn(new Date());

        return ownerReviewRepository.save(ownerReview);
    }

    @Override
    public void delete(long id, User user) {
        ownerReviewRepository.findByIdAndAuthorId(id, user.getId()).orElseThrow(NotFoundException::new);
        ownerReviewRepository.delete(id);
    }

}
