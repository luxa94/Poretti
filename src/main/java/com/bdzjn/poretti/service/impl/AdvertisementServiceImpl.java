package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository){
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public Advertisement create(AdvertisementDTO advertisementDTO, RealEstate realEstate) {
        final Advertisement advertisement = new Advertisement();
        advertisement.setAdvertiser(realEstate.getOwner());
        advertisement.setAnnouncedOn(new Date());
        advertisement.setEditedOn(new Date());
        advertisement.setEndsOn(advertisementDTO.getEndsOn());
        advertisement.setCurrency(advertisementDTO.getCurrency());
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setRealEstate(realEstate);
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setStatus(AdvertisementStatus.ACTIVE);
        advertisement.setType(advertisementDTO.getType());

        return advertisementRepository.save(advertisement);
    }

    @Override
    public Optional<Advertisement> findById(long id) {
        return advertisementRepository.findById(id);
    }

    @Override
    public Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId) {
        return advertisementRepository.findByIdAndOwnerId(id, ownerId);
    }

    @Override
    public Advertisement edit(AdvertisementDTO advertisementDTO, long ownerId) {
        final Advertisement advertisement = findByIdAndOwnerId(advertisementDTO.getId(), ownerId).orElseThrow(NotFoundException::new);
        advertisement.setTitle(advertisementDTO.getTitle());
        advertisement.setCurrency(advertisementDTO.getCurrency());
        advertisement.setPrice(advertisementDTO.getPrice());
        advertisement.setEditedOn(new Date());
        advertisement.setEndsOn(advertisementDTO.getEndsOn());
        advertisement.setType(advertisementDTO.getType());

        if (advertisement.getStatus() == AdvertisementStatus.INVALID) {
            advertisement.setStatus(AdvertisementStatus.PENDING_APPROVAL);
        }

        return advertisementRepository.save(advertisement);
    }

    @Override
    public void delete(long id) {
        advertisementRepository.delete(id);
    }

    @Override
    public void changeStatus(long id, AdvertisementStatus status) {
        final Advertisement advertisement = findById(id).orElseThrow(NotFoundException::new);

        advertisement.setStatus(status);
        advertisementRepository.save(advertisement);
    }

    @Override
    public List<Advertisement> findReported() {
        return advertisementRepository.findReported();
    }

}
