package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.enumeration.RealEstateType;

import java.util.ArrayList;
import java.util.List;

public class RealEstateTestData {

    public static RealEstateDTO testEntity() {
        final Location location = new Location();
        location.setCity("Test city");
        location.setLatitude(2);
        location.setLongitude(2);
        location.setHasLatLong(true);

        final List<String> technicalEquipment = new ArrayList<>();
        technicalEquipment.add("TV");

        RealEstateDTO realEstateDTO = new RealEstateDTO();
        realEstateDTO.setArea(100);
        realEstateDTO.setName("Test name");
        realEstateDTO.setLocation(location);
        realEstateDTO.setImageUrl("/testImage.jpg");
        realEstateDTO.setDescription("Test description");
        realEstateDTO.setTechnicalEquipment(technicalEquipment);
        realEstateDTO.setRealEstateType(RealEstateType.APARTMENT);
        return realEstateDTO;
    }

}
