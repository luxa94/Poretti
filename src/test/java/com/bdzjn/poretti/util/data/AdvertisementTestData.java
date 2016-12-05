package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementRealEstateDTO;
import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;

import java.util.Date;

public class AdvertisementTestData {

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
