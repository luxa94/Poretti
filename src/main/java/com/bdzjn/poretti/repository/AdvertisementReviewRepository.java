package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.AdvertisementReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementReviewRepository extends JpaRepository<AdvertisementReview, Long>, AdvertisementReviewRepositoryCustom {

    Optional<AdvertisementReview> findById(long id);

    Optional<AdvertisementReview> findByIdAndAuthorId(long id, long authorId);

}
