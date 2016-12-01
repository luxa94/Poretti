package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementRealEstateDTO;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.RealEstate;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AdvertisementService {
    Advertisement save(AdvertisementDTO advertisementDTO, RealEstate realEstate);

    Optional<Advertisement> findById(long id);

    Advertisement update(AdvertisementDTO advertisementDTO, long id);
}
