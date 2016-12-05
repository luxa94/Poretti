package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.AdvertisementReportDTO;
import com.bdzjn.poretti.model.enumeration.ImproperReportReason;

public class AdvertisementReportTestData {

    public static AdvertisementReportDTO testEntity() {
        final AdvertisementReportDTO advertisementReport = new AdvertisementReportDTO();
        advertisementReport.setDescription("Test advertisement report");
        advertisementReport.setReason(ImproperReportReason.OTHER);

        return advertisementReport;
    }

}
