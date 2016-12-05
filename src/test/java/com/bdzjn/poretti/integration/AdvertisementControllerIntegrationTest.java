package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.AdvertisementReportTestData;
import com.bdzjn.poretti.util.data.AdvertisementTestData;
import com.bdzjn.poretti.util.data.ReviewTestData;
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
    private static final String AUTHORIZATION = "X-AUTH-TOKEN";
    private static final String TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d8";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ONE_ADVERTISEMENT = BASE_URL + "/1";

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Advertisement title")))
                .andExpect(jsonPath("$.advertiser.id", is(2)))
                .andExpect(jsonPath("$.status", is(AdvertisementStatus.ACTIVE.toString())))
                .andExpect(jsonPath("$.type", is(AdvertisementType.SALE.toString())))
                .andExpect(jsonPath("$.price", is(3000d)))
                .andExpect(jsonPath("$.currency", is(Currency.RSD.toString())))
                .andExpect(jsonPath("$.realEstate.id", is(1)));
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ONE_ADVERTISEMENT = BASE_URL + nonExistingId;

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final AdvertisementRealEstateDTO testEntity = AdvertisementTestData.realEstateAdvertisementTestEntity();

        this.mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.title", is(testEntity.getAdvertisementDTO().getTitle())))
                .andExpect(jsonPath("$.advertiser.id", is(2)))
                .andExpect(jsonPath("$.status", is(AdvertisementStatus.ACTIVE.toString())))
                .andExpect(jsonPath("$.type", is(testEntity.getAdvertisementDTO().getType().toString())))
                .andExpect(jsonPath("$.price", is(testEntity.getAdvertisementDTO().getPrice())))
                .andExpect(jsonPath("$.currency", is(testEntity.getAdvertisementDTO().getCurrency().toString())));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + "/1";
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        testEntity.setTitle("New test title");

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isOk())
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
    public void editShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String EDIT_ADVERTISEMENT = BASE_URL + nonExistingId;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + "/1";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String nonExistingId = "/2";
        final String EDIT_ADVERTISEMENT = BASE_URL + nonExistingId;
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + "/1";

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String DELETE_ADVERTISEMENT = BASE_URL + nonExistingId;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + "/1";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String nonExistingId = "/2";
        final String DELETE_ADVERTISEMENT = BASE_URL + nonExistingId;
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + "/1/reports";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason", is(testEntity.getReason().toString())))
                .andExpect(jsonPath("$.description", is(testEntity.getDescription())))
                .andExpect(jsonPath("$.author.id", is(3)))
                .andExpect(jsonPath("$.advertisement.id", is(1)));
    }

    @Test
    @Transactional
    public void createReportShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + nonExistingId + "/reports";

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + "/1/reports";

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReportsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REPORTS = BASE_URL + "/1/reports";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void findReportsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ADVERTISEMENT_REPORTS = BASE_URL + nonExistingId + "/reports";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + "/1/reviews";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.comment", is(testEntity.getComment())))
                .andExpect(jsonPath("$.rating", is(testEntity.getRating())))
                .andExpect(jsonPath("$.target.id", is(1)));
    }

    @Test
    @Transactional
    public void createReviewShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + nonExistingId + "/reviews";

        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + "/1/reviews";

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REVIEWS = BASE_URL + "/1/reviews";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ADVERTISEMENT_REVIEWS = BASE_URL + nonExistingId + "/reviews";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS))
                .andExpect(status().isNotFound());
    }

}
