package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    Advertisement create(AdvertisementDTO advertisementDTO, RealEstate realEstate);

    Optional<Advertisement> findById(long id);

    Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId);

    Advertisement edit(AdvertisementDTO advertisementDTO, long ownerId);

    void delete(long id);

    void changeStatus(long id, AdvertisementStatus status);

    List<Advertisement> findReported();

}
