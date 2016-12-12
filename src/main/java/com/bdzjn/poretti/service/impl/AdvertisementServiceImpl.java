package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.exception.BadDateException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.ImproperAdvertisementReport;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.repository.AdvertisementReviewRepository;
import com.bdzjn.poretti.repository.ImproperAdvertisementReportRepository;
import com.bdzjn.poretti.service.AdvertisementService;
import com.bdzjn.poretti.service.ImproperAdvertisementReportService;
import com.bdzjn.poretti.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final ImproperAdvertisementReportRepository improperAdvertisementReportRepository;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository,
                                    ImproperAdvertisementReportRepository improperAdvertisementReportRepository) {
        this.advertisementRepository = advertisementRepository;
        this.improperAdvertisementReportRepository = improperAdvertisementReportRepository;
    }

    @Override
    public Advertisement create(AdvertisementDTO advertisementDTO, RealEstate realEstate) {
        final Date endsOn = DateUtils.justDate(advertisementDTO.getEndsOn());
        if (DateUtils.endsOnIsBeforeToday(endsOn)) {
            throw new BadDateException("Ends on cannot be before today");
        }
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
    public List<Advertisement> findReported() {
        return advertisementRepository.findReported();
    }


    @Override
    public Page<Advertisement> findFor(long companyId, AdvertisementSearchCriteria searchCriteria, Pageable pageable) {
        return advertisementRepository.findFor(companyId, searchCriteria, pageable);
    }

    @Override
    public Page<Advertisement> findActiveFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable) {
        return advertisementRepository.findActiveFor(advertiserId, searchCriteria, pageable);
    }

    @Override
    public Page<Advertisement> findActive(AdvertisementSearchCriteria searchCriteria, Pageable pageable) {
        return advertisementRepository.findActive(searchCriteria, pageable);
    }

    @Override
    public Advertisement edit(AdvertisementDTO advertisementDTO, long ownerId) {
        final Advertisement advertisement = findByIdAndOwnerId(advertisementDTO.getId(), ownerId).orElseThrow(NotFoundException::new);
        final Date endsOn = DateUtils.justDate(advertisementDTO.getEndsOn());
        if (DateUtils.endsOnIsBeforeToday(endsOn)) {
            throw new BadDateException("Ends on cannot be before today");
        }
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
    public void deleteReportsAfterApprove(long id) {
        final Advertisement advertisement = findById(id).orElseThrow(NotFoundException::new);
        improperAdvertisementReportRepository.delete(advertisement.getReports());
    }

}
