package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.InvalidRangeException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.controller.exception.UnprocessableEntityException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.AdvertisementReview;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.repository.AdvertisementReviewRepository;
import com.bdzjn.poretti.service.AdvertisementReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdvertisementReviewServiceImpl implements AdvertisementReviewService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementReviewRepository advertisementReviewRepository;

    @Autowired
    public AdvertisementReviewServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementReviewRepository advertisementReviewRepository) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementReviewRepository = advertisementReviewRepository;
    }

    @Override
    public AdvertisementReview create(ReviewDTO reviewDTO, long advertisementId, User author) {
        final Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(NotFoundException::new);
        if (advertisement.getAdvertiser().getId() == author.getId()) {
            throw new ForbiddenException();
        }
        if (advertisement.getStatus() != AdvertisementStatus.ACTIVE) {
            throw new UnprocessableEntityException("Cannot create review on non active advertisement");
        }
        if (reviewDTO.getRating() < 1 || reviewDTO.getRating() > 10) {
            throw new InvalidRangeException("Rating must be in range form 1 to 10");
        }
        final AdvertisementReview review = new AdvertisementReview();
        review.setEditedOn(new Date());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setAuthor(author);
        review.setTarget(advertisement);

        return advertisementReviewRepository.save(review);
    }

    @Override
    public void delete(long id, User user) {
        advertisementReviewRepository.findByIdAndAuthorId(id, user.getId()).orElseThrow(NotFoundException::new);
        advertisementReviewRepository.delete(id);
    }
}
