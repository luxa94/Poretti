package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.enumeration.RealEstateType;

import java.util.ArrayList;
import java.util.List;

public class RealEstateTestData {

    public static final int EXISTING_ID = 1;
    public static final int NON_EXISTING_ID = 212;
    public static final String EXISTING_ID_PATH = "/{id}";
    public static final String NON_EXISTING_ID_PATH = "/{id}";
    public static final String EXISTING_NAME = "Test name";
    public static final String EXISTING_DESCRIPTION = "Test description";
    public static final double EXISTING_AREA = 500d;
    public static final int LOCATION_ID = 1;
    public static final String LOCATION_CITY = "Test city";
    public static final String LOCATION_CITY_AREA = "Test city area";
    public static final String LOCATION_STATE = "Test state";
    public static final String LOCATION_STREET = "Test street";
    public static final double LOCATION_LATITUDE = 1;
    public static final double LOCATION_LONGITUDE = 1;
    public static final String EXISTING_IMAGE = "/images/defaultRealEstate.jpg";
    public static final String EXISTING_TYPE = RealEstateType.APARTMENT.name();
    public static final int OWNER_ID = 2;
    public static final int OCCURRENCE_IN_ADVERTISEMENTS = 1;
    public static final String ID_PATH_VARIABLE = "/{id}";

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
        realEstateDTO.setType(RealEstateType.APARTMENT);
        return realEstateDTO;
    }

}
