package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.RealEstate;

import java.util.Optional;

public interface RealEstateRepositoryCustom {
    Optional<RealEstate> findByIdAndOwnerId(long realEstateId, long ownerId);

}
