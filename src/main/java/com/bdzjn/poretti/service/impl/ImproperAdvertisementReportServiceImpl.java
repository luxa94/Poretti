package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.AdvertisementReportDTO;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.ImproperAdvertisementReport;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.repository.ImproperAdvertisementReportRepository;
import com.bdzjn.poretti.service.ImproperAdvertisementReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImproperAdvertisementReportServiceImpl implements ImproperAdvertisementReportService {

    private final AdvertisementRepository advertisementRepository;
    private final ImproperAdvertisementReportRepository improperAdvertisementReportRepository;

    @Autowired
    public ImproperAdvertisementReportServiceImpl(AdvertisementRepository advertisementRepository, ImproperAdvertisementReportRepository improperAdvertisementReportRepository) {
        this.advertisementRepository = advertisementRepository;
        this.improperAdvertisementReportRepository = improperAdvertisementReportRepository;
    }

    @Override
    public ImproperAdvertisementReport create(AdvertisementReportDTO advertisementReportDTO, long advertisementId, User user) {
        final Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(NotFoundException::new);
        if (advertisement.getAdvertiser().getId() == user.getId()) {
            throw new ForbiddenException();
        }

        final ImproperAdvertisementReport improperAdvertisementReport = new ImproperAdvertisementReport();
        improperAdvertisementReport.setAuthor(user);
        improperAdvertisementReport.setEditedOn(new Date());
        improperAdvertisementReport.setAdvertisement(advertisement);
        improperAdvertisementReport.setReason(advertisementReportDTO.getReason());
        improperAdvertisementReport.setDescription(advertisementReportDTO.getDescription());

        return improperAdvertisementReportRepository.save(improperAdvertisementReport);
    }

}
