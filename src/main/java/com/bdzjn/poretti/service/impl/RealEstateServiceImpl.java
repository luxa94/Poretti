package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.repository.RealEstateRepository;
import com.bdzjn.poretti.service.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RealEstateServiceImpl implements RealEstateService {

    private RealEstateRepository realEstateRepository;

    @Autowired
    public RealEstateServiceImpl(RealEstateRepository realEstateRepository){
        this.realEstateRepository = realEstateRepository;
    }

    public Optional<RealEstate> findById(long id) {
        return realEstateRepository.findById(id);
    }


    @Override
    public RealEstate create(RealEstateDTO realEstateDTO, Owner owner) {
        final RealEstate realEstate = new RealEstate();
        realEstate.setName(realEstate.getName());
        realEstate.setArea(realEstate.getArea());
        realEstate.setTechnicalEquipment(realEstateDTO.getTechnicalEquipment());
        realEstate.setDescription(realEstate.getDescription());
        realEstate.setImageUrl(realEstate.getImageUrl());
        realEstate.setOwner(owner);

        return realEstateRepository.save(realEstate);
    }

    @Override
    public RealEstate edit(RealEstateDTO realEstateDTO, long ownerId) {
        final RealEstate realEstate = findByIdAndOwnerId(realEstateDTO.getId(), ownerId).orElseThrow(NotFoundException::new);
        realEstate.setName(realEstateDTO.getName());
        realEstate.setTechnicalEquipment(realEstateDTO.getTechnicalEquipment());
        realEstate.setDescription(realEstateDTO.getDescription());
        realEstate.setArea(realEstateDTO.getArea());
        realEstate.setLocation(realEstateDTO.getLocation());

        return realEstateRepository.save(realEstate);
    }

    @Override
    public Optional<RealEstate> findByIdAndOwnerId(long id, long ownerId) {
        return realEstateRepository.findByIdAndOwnerId(id, ownerId);
    }

    @Override
    public void delete(long id) {
        realEstateRepository.delete(id);
    }
}
