package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.RealEstate;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RealEstateService {

    RealEstate create(RealEstateDTO realEstateDTO, Owner owner);

    Optional<RealEstate> findById(long id);

    Optional<RealEstate> findByIdAndOwnerId(long id, long ownerId);

    RealEstate edit(RealEstateDTO realEstateDTO, long id);

    void delete(long id);
}
