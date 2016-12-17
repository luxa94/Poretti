package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AdvertisementReportDTO;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.UnprocessableEntityException;
import com.bdzjn.poretti.model.ImproperAdvertisementReport;
import com.bdzjn.poretti.model.User;

public interface ImproperAdvertisementReportService {

    /**
     * Create will succeed if user is not advertiser and if status of advertisement is active
     * In case when user is advertiser {@link ForbiddenException} is thrown
     * In case when advertisement status {@link UnprocessableEntityException} is thrown
     *
     * @param advertisementReportDTO DTO object which containing data for report
     * @param advertisementId Id of advertisement for which this report will be created
     * @param user Author of this report
     * @return
     */
    ImproperAdvertisementReport create(AdvertisementReportDTO advertisementReportDTO, long advertisementId, User user);

}
