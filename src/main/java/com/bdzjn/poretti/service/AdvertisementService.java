package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    Advertisement create(AdvertisementDTO advertisementDTO, RealEstate realEstate);

    Optional<Advertisement> findById(long id);

    Optional<Advertisement> findByIdAndOwnerId(long id, long advertiserId);

    List<Advertisement> findReported();

    Page<Advertisement> findFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    Page<Advertisement> findActiveFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    Page<Advertisement> findActive(AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    Advertisement edit(AdvertisementDTO advertisementDTO, long ownerId);

    void delete(long id);

    void changeStatus(long id, AdvertisementStatus status);

}
