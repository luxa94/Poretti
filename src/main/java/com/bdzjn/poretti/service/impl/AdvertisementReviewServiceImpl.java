package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.AdvertisementReview;
import com.bdzjn.poretti.model.User;
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
        final AdvertisementReview review = new AdvertisementReview();
        review.setEditedOn(new Date());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setAuthor(author);
        review.setTarget(advertisement);

        return advertisementReviewRepository.save(review);
    }
}
