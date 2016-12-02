package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AdvertisementReportDTO;
import com.bdzjn.poretti.model.ImproperAdvertisementReport;
import com.bdzjn.poretti.model.User;
import org.springframework.stereotype.Service;

public interface ImproperAdvertisementReportService {
    ImproperAdvertisementReport create(AdvertisementReportDTO advertisementReportDTO, long advertisementId, User user);
}
