package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.RealEstate;

import java.util.Optional;

public interface RealEstateService {

    RealEstate create(RealEstateDTO realEstateDTO, Owner owner);

    Optional<RealEstate> findById(long id);

    Optional<RealEstate> findByIdAndOwnerId(long id, long ownerId);

    /**
     * Edits existing real estate.
     * In case when real estate doesn't exist or ownerId is not id of actual owner {@link NotFoundException} is thrown.
     *
     * @param realEstateDTO
     * @param ownerId If of owner of real estate.
     * @return
     */
    RealEstate edit(RealEstateDTO realEstateDTO, long ownerId);

    void delete(long id);
}
