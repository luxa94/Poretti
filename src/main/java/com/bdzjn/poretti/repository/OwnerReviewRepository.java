package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.OwnerReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerReviewRepository extends JpaRepository<OwnerReview, Long>, OwnerReviewRepositoryCustom {

    Optional<OwnerReview> findById(long id);

    Optional<OwnerReview> findByIdAndAuthorId(long id, long authorId);

}
