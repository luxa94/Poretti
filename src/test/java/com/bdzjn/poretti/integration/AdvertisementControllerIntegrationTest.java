package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.AdvertisementReportTestData;
import com.bdzjn.poretti.util.data.AdvertisementTestData;
import com.bdzjn.poretti.util.data.ReviewTestData;
import com.bdzjn.poretti.util.data.UserTestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class AdvertisementControllerIntegrationTest {

    private static final String BASE_URL = "/api/advertisements";
    private static final String REPORTS_PATH = "/reports";
    private static final String REVIEWS_PATH = "/reviews";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(AdvertisementTestData.EXISTING_ID)))
                .andExpect(jsonPath("$.title", is(AdvertisementTestData.EXISTING_TITLE)))
                .andExpect(jsonPath("$.advertiser.id", is(AdvertisementTestData.ADVERTISER_ID)))
                .andExpect(jsonPath("$.status", is(AdvertisementTestData.EXISTING_STATUS)))
                .andExpect(jsonPath("$.type", is(AdvertisementTestData.EXISTING_TYPE)))
                .andExpect(jsonPath("$.price", is(AdvertisementTestData.EXISTING_PRICE)))
                .andExpect(jsonPath("$.currency", is(AdvertisementTestData.EXISTING_CURRENCY)))
                .andExpect(jsonPath("$.realEstate.id", is(AdvertisementTestData.CONTAINING_REAL_ESTATE_ID)));
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String FIND_ONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH;

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final AdvertisementRealEstateDTO testEntity = AdvertisementTestData.realEstateAdvertisementTestEntity();

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.title", is(testEntity.getAdvertisementDTO().getTitle())))
                .andExpect(jsonPath("$.advertiser.id", is(UserTestData.CURRENT_USER_ID)))
                .andExpect(jsonPath("$.status", is(AdvertisementStatus.ACTIVE.toString())))
                .andExpect(jsonPath("$.type", is(testEntity.getAdvertisementDTO().getType().toString())))
                .andExpect(jsonPath("$.price", is(testEntity.getAdvertisementDTO().getPrice())))
                .andExpect(jsonPath("$.currency", is(testEntity.getAdvertisementDTO().getCurrency().toString())));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        testEntity.setTitle("New test title");

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.title", is(testEntity.getTitle())))
                .andExpect(jsonPath("$.advertiser.id", is(UserTestData.CURRENT_USER_ID)))
                .andExpect(jsonPath("$.status", is(AdvertisementStatus.ACTIVE.toString())))
                .andExpect(jsonPath("$.type", is(testEntity.getType().toString())))
                .andExpect(jsonPath("$.price", is(testEntity.getPrice())))
                .andExpect(jsonPath("$.currency", is(testEntity.getCurrency().toString())))
                .andExpect(jsonPath("$.realEstate.id", is(AdvertisementTestData.CONTAINING_REAL_ESTATE_ID)));
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + REPORTS_PATH;
        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason", is(testEntity.getReason().toString())))
                .andExpect(jsonPath("$.description", is(testEntity.getDescription())))
                .andExpect(jsonPath("$.author.id", is(AdvertisementReportTestData.REPORT_AUTHOR_ID)))
                .andExpect(jsonPath("$.advertisement.id", is(AdvertisementReportTestData.FROM_ADVERTISEMENT_ID)));
    }

    @Test
    @Transactional
    public void createReportShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH + REPORTS_PATH;

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + REPORTS_PATH;
        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReportsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REPORTS_PATH = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + REPORTS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void findReportsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String FIND_ADVERTISEMENT_REPORTS_PATH = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH + REPORTS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS_PATH))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.comment", is(testEntity.getComment())))
                .andExpect(jsonPath("$.rating", is(testEntity.getRating())))
                .andExpect(jsonPath("$.target.id", is(AdvertisementTestData.EXISTING_ID)));
    }

    @Test
    @Transactional
    public void createReviewShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + REVIEWS_PATH;

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REVIEWS = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String FIND_ADVERTISEMENT_REVIEWS = BASE_URL + AdvertisementTestData.NON_EXISTING_ID + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS))
                .andExpect(status().isNotFound());
    }

}
