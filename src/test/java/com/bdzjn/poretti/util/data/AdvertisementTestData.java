package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementRealEstateDTO;
import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;

import java.util.Date;

public class AdvertisementTestData {

    public static final int EXISTING_ID = 1;
    public static final int NON_EXISTING_ID = 212;
    public static final String EXISTING_ID_PATH = "/1";
    public static final String NON_EXISTING_ID_PATH = "/" + NON_EXISTING_ID;
    public static final String EXISTING_TITLE = "Advertisement title";
    public static final double EXISTING_PRICE = 30000d;
    public static final String EXISTING_TYPE = AdvertisementType.SALE.name();
    public static final String EXISTING_STATUS = AdvertisementStatus.ACTIVE.name();
    public static final String EXISTING_CURRENCY = Currency.RSD.name();
    public static final int CONTAINING_REAL_ESTATE_ID = 1;
    public static final int ADVERTISER_ID = 2;

    public static AdvertisementDTO testEntity() {
        final AdvertisementDTO advertisement = new AdvertisementDTO();
        advertisement.setPrice(100000);
        advertisement.setCurrency(Currency.RSD);
        advertisement.setTitle("Test Advertisement");
        advertisement.setType(AdvertisementType.SALE);
        advertisement.setEndsOn(new Date());
        return advertisement;
    }

    public static AdvertisementRealEstateDTO realEstateAdvertisementTestEntity() {
        final AdvertisementRealEstateDTO advertisementRealEstate = new AdvertisementRealEstateDTO();

        final AdvertisementDTO advertisement = AdvertisementTestData.testEntity();
        final RealEstateDTO realEstate = RealEstateTestData.testEntity();

        advertisementRealEstate.setAdvertisementDTO(advertisement);
        advertisementRealEstate.setRealEstateDTO(realEstate);

        return advertisementRealEstate;
    }

}
