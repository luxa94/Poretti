package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.AdvertisementTestData;
import com.bdzjn.poretti.util.data.RealEstateTestData;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class RealEstateControllerIntegrationTest {

    private static final String BASE_URL = "/api/realEstates";
    private static final String AUTHORIZATION = "X-AUTH-TOKEN";
    private static final String TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d8";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenRealEstateExists() throws Exception {
        final String FIND_ONE_REAL_ESTATE = BASE_URL + "/1";

        this.mockMvc.perform(get(FIND_ONE_REAL_ESTATE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test name")))
                .andExpect(jsonPath("$.description", is("Test description")))
                .andExpect(jsonPath("$.area", is(100d)))
                .andExpect(jsonPath("$.location.id", is(1)))
                .andExpect(jsonPath("$.imageUrl", is("/images/defaultRealEstate.jpg")))
                .andExpect(jsonPath("$.type", is(RealEstateType.APARTMENT.toString())))
                .andExpect(jsonPath("$.owner.id", is(2)));
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ONE_REAL_ESTATE = BASE_URL + nonExistingId;

        this.mockMvc.perform(get(FIND_ONE_REAL_ESTATE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name", is(testEntity.getName())))
                .andExpect(jsonPath("$.description", is(testEntity.getDescription())))
                .andExpect(jsonPath("$.area", is(testEntity.getArea())))
                .andExpect(jsonPath("$.imageUrl", is(testEntity.getImageUrl())))
                .andExpect(jsonPath("$.type", is(testEntity.getRealEstateType().toString())))
                .andExpect(jsonPath("$.technicalEquipment", hasSize(1)))
                .andExpect(jsonPath("$.owner.id", is(2)));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenRealEstateExistsAndCurrentUserIsOwner() throws Exception{
        final String EDIT_REAL_ESTATE = BASE_URL + "/1";
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        testEntity.setName("Real estate new test name");

        this.mockMvc.perform(put(EDIT_REAL_ESTATE)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name", is(testEntity.getName())))
                .andExpect(jsonPath("$.description", is(testEntity.getDescription())))
                .andExpect(jsonPath("$.area", is(testEntity.getArea())))
                .andExpect(jsonPath("$.imageUrl", is(testEntity.getImageUrl())))
                .andExpect(jsonPath("$.type", is(testEntity.getRealEstateType().toString())))
                .andExpect(jsonPath("$.technicalEquipment", hasSize(1)))
                .andExpect(jsonPath("$.owner.id", is(2)));
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception{
        final String nonExistingId = "/2";
        final String EDIT_REAL_ESTATE = BASE_URL + nonExistingId;

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(put(EDIT_REAL_ESTATE)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotOwner() throws Exception {
        final String EDIT_REAL_ESTATE = BASE_URL + "/1";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(put(EDIT_REAL_ESTATE)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotOwnerAndNonExistingRealEstate() throws Exception {
        final String nonExistingId = "/2";
        final String EDIT_REAL_ESTATE = BASE_URL + nonExistingId;
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(put(EDIT_REAL_ESTATE)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenRealEstateExistsAndCurrentUserIsOwner() throws Exception {
        final String DELETE_REAL_ESTATE = BASE_URL + "/1";

        this.mockMvc.perform(delete(DELETE_REAL_ESTATE)
                .header(AUTHORIZATION, TOKEN_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String nonExistingId = "/2";
        final String DELETE_REAL_ESTATE = BASE_URL + nonExistingId;

        this.mockMvc.perform(delete(DELETE_REAL_ESTATE)
                .header(AUTHORIZATION, TOKEN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotOwner() throws Exception {
        final String DELETE_REAL_ESTATE = BASE_URL + "/1";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        this.mockMvc.perform(delete(DELETE_REAL_ESTATE)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void findAdvertisementsShouldReturnOkWhenRealEstateExists() throws Exception {
        final String FIND_REAL_ESTATE_ADVERTISEMENTS = BASE_URL + "/1/advertisements";

        this.mockMvc.perform(get(FIND_REAL_ESTATE_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional
    public void findAdvertisementsShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_REAL_ESTATE_ADVERTISEMENTS = BASE_URL + nonExistingId + "/advertisement";

        this.mockMvc.perform(get(FIND_REAL_ESTATE_ADVERTISEMENTS))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnCreatedWhenRealEstateExistsAndCurrentUserIsOwner() throws Exception {
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + "/1/advertisements";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.title", is(testEntity.getTitle())))
                .andExpect(jsonPath("$.advertiser.id", is(2)))
                .andExpect(jsonPath("$.status", is(AdvertisementStatus.ACTIVE.toString())))
                .andExpect(jsonPath("$.type", is(testEntity.getType().toString())))
                .andExpect(jsonPath("$.price", is(testEntity.getPrice())))
                .andExpect(jsonPath("$.currency", is(testEntity.getCurrency().toString())))
                .andExpect(jsonPath("$.realEstate.id", is(1)));
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String nonExistingId = "/2";
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + nonExistingId + "/advertisements";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnNotFoundWhenCurrentUserIsNotOwner() throws Exception {
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + "/1/advertisements";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnNotFoundWhenCurrentIsNotOwnerAndNonExistingRE() throws Exception {
        final String nonExistingId = "/2";
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + nonExistingId + "/advertisements";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

}
