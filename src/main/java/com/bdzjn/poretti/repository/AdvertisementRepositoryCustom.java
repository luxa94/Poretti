package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepositoryCustom {

    Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId);

    List<Advertisement> findReported();

    Page<Advertisement> findFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    Page<Advertisement> findActiveFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    Page<Advertisement> findActive(AdvertisementSearchCriteria searchCriteria, Pageable pageable);
}
