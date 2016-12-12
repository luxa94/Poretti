package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;

import java.util.ArrayList;
import java.util.List;

public class CompanyTestData {

    public static final String REAL_ESTATES_PATH = "/realEstates";
    public static final String ADVERTISEMENTS_PATH = "/advertisements";
    public static final String MEMBERSHIPS_PATH = "/memberships";
    public static final String REVIEWS_PATH = "/reviews";

    public static final String EXISTING_COMPANY_ID = "/5";
    public static final String NON_EXISTING_COMPANY_ID = "/123";
    public static final String EXISTING_COMPANY_REAL_ESTATE_ID = "/2";
    public static final String EXISTING_COMPANY_ADVERTISEMENT_ID = "/2";
    public static final String UNCONFIRMED_MEMBERSHIP_ID = "/2";
    public static final String CONFIRMED_MEMBERSHIP_ID = "/1";

    public static final String USER_CONFIRMED_MEMBERSHIP = "102da414-847d-4602-8b2d-edca26ab26d8";
    public static final String USER_NOT_CONFIRMED_MEMBERSHIP = "102da414-847d-4602-8b2d-edca26ab26d9";
    public static final String USER_NO_MEMBERSHIP = "102da414-847d-4602-8b2d-edca26ab26e8";

    public static final String FREE_TEST_PIB = "test_pib";
    public static final String EXISTING_PIB = "pib";
    public static final String COMPANY_ADMIN_PASSWORD = "iiscompanyadmin";
    public static final String COMPANY_ADMIN_FREE_USERNAME = "companyUsername";
    public static final String COMPANY_ADMIN_FREE_EMAIL = "companyadmin@test.com";
    public static final String TEST_COMPANY_NAME = "TEST_COMPANY";
    public static final String TEST_COMPANY_CITY = "TEST_CITY";
    public static final String EDITED_REAL_ESTATE_DESCRIPTION = "TEST_DESCRIPTION";
    public static final double EDITED_AREA = 500d;
    public static final String EDITED_ADVERTISEMENT_TITLE = "EDITED_TITLE";
    public static final double EDITED_ADVERTISEMENT_PRICE = 1000;

    public static RegisterCompanyDTO testEntity() {
        final RegisterCompanyDTO registerCompanyDTO = new RegisterCompanyDTO();

        final CompanyDTO companyDTO = getCompanyDTO();
        registerCompanyDTO.setCompanyDTO(companyDTO);

        final RegisterDTO registerDTO = getRegisterDTO();
        registerCompanyDTO.setRegisterDTO(registerDTO);

        return registerCompanyDTO;
    }

    public static RegisterDTO getRegisterDTO() {
        final RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail(COMPANY_ADMIN_FREE_EMAIL);
        registerDTO.setUsername(COMPANY_ADMIN_FREE_USERNAME);
        registerDTO.setPassword(COMPANY_ADMIN_PASSWORD);
        registerDTO.setName("Company admin");
        return registerDTO;
    }

    public static CompanyDTO getCompanyDTO() {
        final CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setName(TEST_COMPANY_NAME);
        final Location location = new Location();
        location.setCity(TEST_COMPANY_CITY);
        companyDTO.setLocation(location);
        companyDTO.setPib(FREE_TEST_PIB);
        return companyDTO;
    }

    public static RealEstateDTO editedRealEstate() {
        final RealEstateDTO realEstateDTO = RealEstateTestData.testEntity();
        realEstateDTO.setDescription(EDITED_REAL_ESTATE_DESCRIPTION);
        final List<String> technicalEquipment = new ArrayList<>();
        technicalEquipment.add("Iron");
        technicalEquipment.add("Stove");
        realEstateDTO.setTechnicalEquipment(technicalEquipment);
        realEstateDTO.setArea(EDITED_AREA);
        return realEstateDTO;
    }

    public static AdvertisementDTO editedAdvertisementDTO() {
        final AdvertisementDTO advertisementDTO = AdvertisementTestData.testEntity();
        advertisementDTO.setTitle(EDITED_ADVERTISEMENT_TITLE);
        advertisementDTO.setPrice(EDITED_ADVERTISEMENT_PRICE);
        advertisementDTO.setType(AdvertisementType.RENT);
        return advertisementDTO;
    }

}
