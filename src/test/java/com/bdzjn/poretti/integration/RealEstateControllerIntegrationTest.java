package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class RealEstateControllerIntegrationTest {

    private static final String URL_PREFIX = "/api/realEstates";
    private static final String AUTHORIZATION = "X-AUTH-TOKEN";
    private static final String TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d8";

    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenRealEstateExists() throws Exception {
        final String FIND_ONE_REAL_ESTATE = URL_PREFIX + "/1";

        this.mockMvc.perform(get(FIND_ONE_REAL_ESTATE))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ONE_REAL_ESTATE = URL_PREFIX + nonExistingId;

        this.mockMvc.perform(get(FIND_ONE_REAL_ESTATE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final String CREATE_REAL_ESTATE = URL_PREFIX;

        RealEstateDTO testEntity = realEstateTestEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated());

    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenRealEstateExistsAndCurrentUserIsOwner(){

    }

    private RealEstateDTO realEstateTestEntity() {
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
