package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementRealEstateDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository){
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public Advertisement save(AdvertisementDTO advertisementDTO, RealEstate realEstate) {
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertiser(realEstate.getOwner());
        advertisement.setAnnouncedOn(advertisementDTO.getAnnouncedOn());
        advertisement.setCurrency(advertisementDTO.getCurrency());
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setRealEstate(realEstate);
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setStatus(AdvertisementStatus.NEW);
        advertisement.setType(advertisementDTO.getType());

        return advertisementRepository.save(advertisement);
    }

    @Override
    public Optional<Advertisement> findById(long id) {
        return advertisementRepository.findById(id);
    }

    @Override
    public Advertisement update(AdvertisementDTO advertisementDTO, long ownerId) {
        final Advertisement advertisement = findByIdAndOwnerId(advertisementDTO.getId(), ownerId).orElseThrow(NotFoundException::new);
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setCurrency(advertisementDTO.getCurrency());
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setEditedOn(advertisementDTO.getEditedOn());
        advertisement.setType(advertisementDTO.getType());
        return advertisementRepository.save(advertisement);
    }

    private Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId) {
        return advertisementRepository.findByIdAndOwnerId(id, ownerId);
    }
}
