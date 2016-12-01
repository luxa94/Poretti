package com.bdzjn.poretti.controller.dto;

import com.bdzjn.poretti.model.enumeration.ImproperReportReason;

public class AdvertisementReportDTO {

    private ImproperReportReason reason;

    private String description;

    public ImproperReportReason getReason() {
        return reason;
    }

    public void setReason(ImproperReportReason reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
