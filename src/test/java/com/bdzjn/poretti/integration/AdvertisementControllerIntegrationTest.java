package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasItem;
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
    private static final String REPORTED_PATH = "/reported";
    private static final String APPROVE_PATH = "/approve";
    private static final String INVALIDATE_PATH = "/invalidate";
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Test
    @Transactional
    public void findShouldReturnOkWhenNoFilters() throws Exception {
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE;
        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForRealEstateNameFilter() throws Exception {
        final String filter = "name";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&realEstateName=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.name", hasItem(RealEstateTestData.EXISTING_NAME)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForRealEstateNameFilter() throws Exception {
        final String filter = "non existing name";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&realEstateName=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAreaFromFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&areaFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.area", hasItem(RealEstateTestData.EXISTING_AREA)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAreaFromFilter() throws Exception {
        final double filter = 50000;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&areaFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAreaToFilter() throws Exception {
        final double filter = 150;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&areaTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.area", hasItem(RealEstateTestData.EXISTING_AREA)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAreaToFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&areaTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForCityFilter() throws Exception {
        final String filter = "city";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&city=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.city", hasItem(RealEstateTestData.LOCATION_CITY)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForCityFilter() throws Exception {
        final String filter = "non existing city";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&city=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForCityAreaFilter() throws Exception {
        final String filter = " city area";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&cityArea=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.cityArea", hasItem(RealEstateTestData.LOCATION_CITY_AREA)));

    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForCityAreaFilter() throws Exception {
        final String filter = "non existing city area";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&cityArea=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForStateFilter() throws Exception {
        final String filter = "state";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&state=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.state", hasItem(RealEstateTestData.LOCATION_STATE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForStateFilter() throws Exception {
        final String filter = "non existing state";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&state=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForStreetFilter() throws Exception {
        final String filter = "street";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&street=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.street", hasItem(RealEstateTestData.LOCATION_STREET)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForStreetFilter() throws Exception {
        final String filter = "non exsiting street";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&street=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForLatitudeFilter() throws Exception {
        final double filter = 1;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&latitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.latitude", hasItem(RealEstateTestData.LOCATION_LATITUDE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForLatitudeFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&latitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForLongitudeFilter() throws Exception {
        final double filter = 1;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&longitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.latitude", hasItem(RealEstateTestData.LOCATION_LONGITUDE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForLongitudeFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&longitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForRealEstateTypeFilter() throws Exception {
        final String filter = RealEstateTestData.EXISTING_TYPE;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&realEstateType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.type", hasItem(RealEstateTestData.EXISTING_TYPE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForRealEstateTypeFilter() throws Exception {
        final RealEstateType filter = RealEstateType.AGRICULTURAL;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&realEstateType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAdvertisementTitleFilter() throws Exception {
        final String filter = "title";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&advertisementTitle=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].title", hasItem(AdvertisementTestData.EXISTING_TITLE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAdvertisementTitleFilter() throws Exception {
        final String filter = "non existing title";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&advertisementTitle=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAdvertisementTypeFilter() throws Exception {
        final AdvertisementType filter = AdvertisementType.SALE;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&advertisementType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].type", hasItem(AdvertisementTestData.EXISTING_TYPE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAdvertisementTypeFilter() throws Exception {
        final AdvertisementType filter = AdvertisementType.RENT;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&advertisementType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForPriceFromFilter() throws Exception {
        final double filter = 1000;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&priceFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].price", hasItem(AdvertisementTestData.EXISTING_PRICE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForPriceFromFilter() throws Exception {
        final double filter = 5000000;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&priceFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForPriceToFilter() throws Exception {
        final double filter = 5000;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&priceTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].price", hasItem(AdvertisementTestData.EXISTING_PRICE)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForPriceToFilter() throws Exception {
        final double filter = 100;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&priceTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForCurrencyFilter() throws Exception {
        final String filter = AdvertisementTestData.EXISTING_CURRENCY.toString();
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&currency=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].currency", hasItem(AdvertisementTestData.EXISTING_CURRENCY)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForCurrencyFilter() throws Exception {
        final Currency filter = Currency.USD;
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&currency=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkWhenFilterForTitleExists() throws Exception {
        final String filterTitle = "title";
        final String FIND_ADVERTISEMENTS = BASE_URL + "?page=" + PAGE + "&size=" + PAGE_SIZE + "&advertisementTitle=" + filterTitle;
        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].title", hasItem(AdvertisementTestData.EXISTING_TITLE)));
    }

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
        final int numberOfElementsBefore = advertisementRepository.findAll().size();
        int numberOfElementsAfter;

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

        numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore+1));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        testEntity.setTitle("New test title");
        final int numberOfElementsBefore = advertisementRepository.findAll().size();
        int numberOfElementsAfter;

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

        numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
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
        final int numberOfElementsBefore = advertisementRepository.findAll().size();
        int numberOfElementsAfter;

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andExpect(status().isOk());

        numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore-1));

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

    @Test
    @Transactional
    public void findReportedShouldReturnOk() throws Exception {
        final String FIND_REPORTED = BASE_URL + REPORTED_PATH;

        this.mockMvc.perform(get(FIND_REPORTED)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void invalidateShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String INVALIDATE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + INVALIDATE_PATH;

        this.mockMvc.perform(put(INVALIDATE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void invalidateShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String INVALIDATE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH + INVALIDATE_PATH;

        this.mockMvc.perform(put(INVALIDATE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void approveShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String APPROVE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.EXISTING_ID_PATH + APPROVE_PATH;

        this.mockMvc.perform(put(APPROVE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void approveShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String APPROVE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.NON_EXISTING_ID_PATH + APPROVE_PATH;

        this.mockMvc.perform(put(APPROVE_ADVERTISEMENT)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
