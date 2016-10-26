package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.ImproperAdvertisementReport;
import com.bdzjn.poretti.repository.ImproperAdvertisementReportRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class ImproperAdvertisementReportRepositoryImpl extends QueryDslRepositorySupport implements ImproperAdvertisementReportRepositoryCustom {

    public ImproperAdvertisementReportRepositoryImpl() {
        super(ImproperAdvertisementReport.class);
    }

}
