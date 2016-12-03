package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.ImproperReportReason;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class AdvertisementControllerIntegrationTest {

    private static final String URL_PREFIX = "/api/advertisements";
    private static final String AUTHORIZATION = "X-AUTH-TOKEN";
    private static final String TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d8";

    @Autowired
    MockMvc mockMvc;

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ONE_ADVERTISEMENT = URL_PREFIX + "/1";

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ONE_ADVERTISEMENT = URL_PREFIX + nonExistingId;

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final String CREATE_ADVERTISEMENT = URL_PREFIX;

        final AdvertisementRealEstateDTO testEntity = realEstateAdvertisementTestEntity();

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = URL_PREFIX + "/1";
        AdvertisementDTO testEntity = advertisementTestEntity();
        testEntity.setTitle("New test title");

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New test title"));

    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String EDIT_ADVERTISEMENT = URL_PREFIX + nonExistingId;

        AdvertisementDTO testEntity = advertisementTestEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {

        final String EDIT_ADVERTISEMENT = URL_PREFIX + "/1";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        AdvertisementDTO testEntity = advertisementTestEntity();

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
        String EDIT_ADVERTISEMENT = URL_PREFIX + nonExistingId;
        String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        AdvertisementDTO testEntity = advertisementTestEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = URL_PREFIX + "/1";
        AdvertisementDTO testEntity = advertisementTestEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String DELETE_ADVERTISEMENT = URL_PREFIX + nonExistingId;

        AdvertisementDTO testEntity = advertisementTestEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {

        final String DELETE_ADVERTISEMENT = URL_PREFIX + "/1";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        AdvertisementDTO testEntity = advertisementTestEntity();

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
        String DELETE_ADVERTISEMENT = URL_PREFIX + nonExistingId;
        String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        AdvertisementDTO testEntity = advertisementTestEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = URL_PREFIX + "/1/reports";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        AdvertisementReportDTO testEntity = advertisementReportTestEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void createReportShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String nonExistingId = "/2";
        final String CREATE_ADVERTISEMENT_REPORT = URL_PREFIX + nonExistingId + "/reports";

        AdvertisementReportDTO testEntity = advertisementReportTestEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = URL_PREFIX + "/1/reports";

        AdvertisementReportDTO testEntity = advertisementReportTestEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReportsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REPORTS = URL_PREFIX + "/1/reports";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void findReportsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ADVERTISEMENT_REPORTS = URL_PREFIX + nonExistingId + "/reports";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = URL_PREFIX + "/1/reviews";
        final String NOT_ADVERTISER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";

        ReviewDTO testEntity = reviewTestEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(AUTHORIZATION, NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated());
    }
    
    @Test
    @Transactional
    public void createReviewShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception{
        final String nonExistingId = "/2";
        final String CREATE_ADVERTISEMENT_REVIEW = URL_PREFIX + nonExistingId + "/reviews";

        ReviewDTO testEntity = reviewTestEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = URL_PREFIX + "/1/reviews";

        AdvertisementReportDTO testEntity = advertisementReportTestEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REVIEWS  = URL_PREFIX + "/1/reviews";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String nonExistingId = "/2";
        final String FIND_ADVERTISEMENT_REVIEWS = URL_PREFIX + nonExistingId + "/reviews";

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS))
                .andExpect(status().isNotFound());
    }

    private AdvertisementRealEstateDTO realEstateAdvertisementTestEntity() {
        AdvertisementRealEstateDTO advertisementRealEstate = new AdvertisementRealEstateDTO();

        AdvertisementDTO advertisement = advertisementTestEntity();
        RealEstateDTO realEstate = realEstateTestEntity();

        advertisementRealEstate.setAdvertisementDTO(advertisement);
        advertisementRealEstate.setRealEstateDTO(realEstate);

        return advertisementRealEstate;
    }

    private AdvertisementDTO advertisementTestEntity() {
        AdvertisementDTO advertisement = new AdvertisementDTO();
        advertisement.setPrice(100000);
        advertisement.setCurrency(Currency.RSD);
        advertisement.setTitle("Test Advertisement");
        advertisement.setType(AdvertisementType.SALE);
        advertisement.setEndsOn(new Date());
        return advertisement;
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

    private AdvertisementReportDTO advertisementReportTestEntity() {
        final AdvertisementReportDTO advertisementReport = new AdvertisementReportDTO();
        advertisementReport.setDescription("Test advertisement report");
        advertisementReport.setReason(ImproperReportReason.OTHER);

        return advertisementReport;
    }

    private ReviewDTO reviewTestEntity() {
        final ReviewDTO review = new ReviewDTO();
        review.setComment("Test review comment");
        review.setRating(5);

        return review;
    }

}
