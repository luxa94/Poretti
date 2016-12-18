package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementRealEstateDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementReportDTO;
import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.repository.AdvertisementRepository;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.*;
import com.bdzjn.poretti.util.snippets.AdvertisementSnippets;
import com.bdzjn.poretti.util.snippets.AuthorizationSnippets;
import com.bdzjn.poretti.util.snippets.ReportSnippets;
import com.bdzjn.poretti.util.snippets.ReviewSnippets;
import com.bdzjn.poretti.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ActiveProfiles(profiles = "test")
public class AdvertisementControllerTest {

    private static final String BASE_URL = "/api/advertisements";
    private static final String REPORTS_PATH = "/reports";
    private static final String REVIEWS_PATH = "/reviews";
    private static final String REPORTED_PATH = "/reported";
    private static final String APPROVE_PATH = "/approve";
    private static final String INVALIDATE_PATH = "/invalidate";
    private static final String DONE_PATH = "/done";
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 5;
    private static final String PAGING = "?page=" + PAGE + "&size=" + PAGE_SIZE;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Test
    @Transactional
    public void findShouldReturnOkWhenNoFilters() throws Exception {
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING;
        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForRealEstateNameFilter() throws Exception {
        final String filter = "name";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&realEstateName=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.name", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForRealEstateNameFilter() throws Exception {
        final String filter = "non existing name";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&realEstateName=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAreaFromFilter() throws Exception {
        final double filter = 150;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&areaFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.area", everyItem(greaterThanOrEqualTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAreaFromFilter() throws Exception {
        final double filter = 50000;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&areaFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.area", everyItem(greaterThanOrEqualTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAreaToFilter() throws Exception {
        final double filter = 150;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&areaTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.area", everyItem(lessThan(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAreaToFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&areaTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.area", everyItem(lessThan(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForCityFilter() throws Exception {
        final String filter = "city";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&city=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.city", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForCityFilter() throws Exception {
        final String filter = "non existing city";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&city=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.city", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForCityAreaFilter() throws Exception {
        final String filter = " city area";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&cityArea=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.cityArea", everyItem(containsString(filter))));

    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForCityAreaFilter() throws Exception {
        final String filter = "non existing city area";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&cityArea=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.cityArea", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForStateFilter() throws Exception {
        final String filter = "state";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&state=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.state", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForStateFilter() throws Exception {
        final String filter = "non existing state";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&state=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.state", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForStreetFilter() throws Exception {
        final String filter = "street";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&street=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.street", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForStreetFilter() throws Exception {
        final String filter = "non existing street";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&street=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForLatitudeFilter() throws Exception {
        final double filter = 1;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&latitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.latitude", everyItem(greaterThanOrEqualTo(filter - 0.01))))
                .andExpect(jsonPath("$.content.[*].realEstate.location.latitude", everyItem(lessThanOrEqualTo(filter + 0.01))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForLatitudeFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&latitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.latitude", everyItem(greaterThanOrEqualTo(filter - 0.01))))
                .andExpect(jsonPath("$.content.[*].realEstate.location.latitude", everyItem(lessThanOrEqualTo(filter + 0.01))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForLongitudeFilter() throws Exception {
        final double filter = 1;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&longitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.longitude", everyItem(greaterThanOrEqualTo(filter - 0.01))))
                .andExpect(jsonPath("$.content.[*].realEstate.location.longitude", everyItem(lessThanOrEqualTo(filter + 0.01))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForLongitudeFilter() throws Exception {
        final double filter = 50;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&longitude=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.location.longitude", everyItem(greaterThanOrEqualTo(filter - 0.01))))
                .andExpect(jsonPath("$.content.[*].realEstate.location.longitude", everyItem(lessThanOrEqualTo(filter + 0.01))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForRealEstateTypeFilter() throws Exception {
        final String filter = RealEstateTestData.EXISTING_TYPE;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&realEstateType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].realEstate.type", everyItem(equalTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForRealEstateTypeFilter() throws Exception {
        final RealEstateType filter = RealEstateType.AGRICULTURAL;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&realEstateType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].realEstate.type", everyItem(equalTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAdvertisementTitleFilter() throws Exception {
        final String filter = "title";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&advertisementTitle=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].title", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAdvertisementTitleFilter() throws Exception {
        final String filter = "non existing title";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&advertisementTitle=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].title", everyItem(containsString(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForAdvertisementTypeFilter() throws Exception {
        final String filter = AdvertisementType.SALE.name();
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&advertisementType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].type", hasItem(filter)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForAdvertisementTypeFilter() throws Exception {
        final String filter = AdvertisementType.RENT.name();
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&advertisementType=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].type", hasItem(filter)));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForPriceFromFilter() throws Exception {
        final double filter = 1000;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&priceFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].price", everyItem(greaterThanOrEqualTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForPriceFromFilter() throws Exception {
        final double filter = 5000000;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&priceFrom=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].price", everyItem(greaterThanOrEqualTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForPriceToFilter() throws Exception {
        final double filter = 5000;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&priceTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].price", everyItem(lessThanOrEqualTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForPriceToFilter() throws Exception {
        final double filter = 100;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&priceTo=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].price", everyItem(lessThanOrEqualTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndNotEmptyContentForCurrencyFilter() throws Exception {
        final String filter = AdvertisementTestData.EXISTING_CURRENCY;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&currency=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].currency", everyItem(equalTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkAndEmptyContentForCurrencyFilter() throws Exception {
        final Currency filter = Currency.USD;
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&currency=" + filter;

        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.content.[*].currency", everyItem(equalTo(filter))));
    }

    @Test
    @Transactional
    public void findShouldReturnOkWhenFilterForTitleExists() throws Exception {
        final String filterTitle = "title";
        final String FIND_ADVERTISEMENTS = BASE_URL + PAGING + "&advertisementTitle=" + filterTitle;
        this.mockMvc.perform(get(FIND_ADVERTISEMENTS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].title", everyItem(containsString(filterTitle))));
    }

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is(AdvertisementTestData.EXISTING_ID)))
                .andExpect(jsonPath("$.title", is(AdvertisementTestData.EXISTING_TITLE)))
                .andExpect(jsonPath("$.advertiser.id", is(AdvertisementTestData.ADVERTISER_ID)))
                .andExpect(jsonPath("$.status", is(AdvertisementTestData.EXISTING_STATUS)))
                .andExpect(jsonPath("$.type", is(AdvertisementTestData.EXISTING_TYPE)))
                .andExpect(jsonPath("$.price", is(AdvertisementTestData.EXISTING_PRICE)))
                .andExpect(jsonPath("$.currency", is(AdvertisementTestData.EXISTING_CURRENCY)))
                .andExpect(jsonPath("$.realEstate.id", is(AdvertisementTestData.CONTAINING_REAL_ESTATE_ID)))
                .andDo(document("find-one-advertisement",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        responseFields(AdvertisementSnippets.ADVERTISEMENT)));
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String FIND_ONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(get(FIND_ONE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final AdvertisementRealEstateDTO testEntity = AdvertisementTestData.realEstateAdvertisementTestEntity();
        final int numberOfElementsBefore = advertisementRepository.findAll().size();

        this.mockMvc.perform(post(BASE_URL)
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
                .andExpect(jsonPath("$.currency", is(testEntity.getAdvertisementDTO().getCurrency().toString())))
                .andDo(document("create-advertisement",
                        preprocessRequest(prettyPrint(), modifyParameters().remove("advertisementDTO.id"), modifyParameters().remove("realEstateDTO.id")),
                        preprocessResponse(prettyPrint()),
                        AuthorizationSnippets.AUTH_HEADER,
                        AdvertisementSnippets.CREATE_REQUEST));

        final int numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore + 1));
    }

    @Test
    @Transactional
    public void createShouldReturnUnprocessableWhenNotProperEndsOnDate() throws Exception {
        final AdvertisementRealEstateDTO testEntity = AdvertisementTestData.realEstateAdvertisementTestEntity();
        final Date yesterday = DateUtils.yesterday();
        testEntity.getAdvertisementDTO().setEndsOn(yesterday);
        final int numberOfElementsBefore = advertisementRepository.findAll().size();

        this.mockMvc.perform(post(BASE_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        final int numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        testEntity.setTitle("New test title");
        final int numberOfElementsBefore = advertisementRepository.findAll().size();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
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
                .andExpect(jsonPath("$.realEstate.id", is(AdvertisementTestData.CONTAINING_REAL_ESTATE_ID)))
                .andDo(document("edit-advertisement",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(AdvertisementSnippets.ADVERTISEMENT_DTO)));

        final int numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnUnprocessableWhenNotProperEndsOnDate() throws Exception {
        final String EDIT_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        final Date yesterday = DateUtils.yesterday();
        testEntity.setEndsOn(yesterday);

        this.mockMvc.perform(put(EDIT_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenAdvertisementExistsAndCurrentUserIsAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final int numberOfElementsBefore = advertisementRepository.findAll().size();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andExpect(status().isOk())
                .andDo(document("delete-advertisement",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER));

        final int numberOfElementsAfter = advertisementRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore - 1));

    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String DELETE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE;
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(delete(DELETE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REPORTS_PATH;
        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.reason", is(testEntity.getReason().toString())))
                .andExpect(jsonPath("$.description", is(testEntity.getDescription())))
                .andExpect(jsonPath("$.author.id", is(AdvertisementReportTestData.REPORT_AUTHOR_ID)))
                .andExpect(jsonPath("$.advertisement.id", is(AdvertisementReportTestData.FROM_ADVERTISEMENT_ID)))
                .andDo(document("create-advertisement-report",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(ReportSnippets.AD_REPORT)));
    }

    @Test
    @Transactional
    public void createReportShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REPORTS_PATH;

        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReportShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REPORTS_PATH;
        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createReportShouldReturnUnprocessableWhenAdvertisementStatusIsNotActive() throws Exception {
        final String CREATE_ADVERTISEMENT_REPORT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REPORTS_PATH;
        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        //TODO: change this to use test suite (if you have time :) )
        final Advertisement advertisement = advertisementRepository.findById(AdvertisementTestData.EXISTING_ID).orElseThrow(NotFoundException::new);
        advertisement.setStatus(AdvertisementStatus.DONE);
        advertisementRepository.save(advertisement);

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REPORT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void findReportsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REPORTS_PATH = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REPORTS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS_PATH, AdvertisementTestData.EXISTING_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(document("find-advertisement-reports",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID)));
    }

    @Test
    @Transactional
    public void findReportsShouldReturnNotFoundWhenNonExistingAdvertisements() throws Exception {
        final String FIND_ADVERTISEMENT_REPORTS_PATH = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REPORTS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REPORTS_PATH, AdvertisementTestData.NON_EXISTING_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnCreatedWhenAdvertisementExistsAndCurrentUserIsNotAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.comment", is(testEntity.getComment())))
                .andExpect(jsonPath("$.rating", is(testEntity.getRating())))
                .andExpect(jsonPath("$.target.id", is(AdvertisementTestData.EXISTING_ID)))
                .andDo(document("create-advertisement-review",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(ReviewSnippets.AD_REVIEW)));
    }

    @Test
    @Transactional
    public void createReviewShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnForbiddenWhenCurrentUserIsAdvertiser() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final AdvertisementReportDTO testEntity = AdvertisementReportTestData.testEntity();

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnUnprocessableWhenAdvertisementStatusIsNotActive() throws Exception {
        final String CREATE_ADVERTISEMENT_REVIEW = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        //TODO: change this alo to use test suite
        final Advertisement advertisement = advertisementRepository.findById(AdvertisementTestData.EXISTING_ID).orElseThrow(NotFoundException::new);
        advertisement.setStatus(AdvertisementStatus.DONE);
        advertisementRepository.save(advertisement);

        this.mockMvc.perform(post(CREATE_ADVERTISEMENT_REVIEW, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String FIND_ADVERTISEMENT_REVIEWS = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS, AdvertisementTestData.EXISTING_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(document("find-advertisement-reviews",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID)));
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String FIND_ADVERTISEMENT_REVIEWS = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_ADVERTISEMENT_REVIEWS, AdvertisementTestData.NON_EXISTING_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void findReportedShouldReturnOk() throws Exception {
        final String FIND_REPORTED = BASE_URL + REPORTED_PATH;

        this.mockMvc.perform(get(FIND_REPORTED)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("find-reported-advertisements",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        AuthorizationSnippets.AUTH_HEADER));
    }

    @Test
    @Transactional
    public void invalidateShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String INVALIDATE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + INVALIDATE_PATH;

        this.mockMvc.perform(put(INVALIDATE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("invalidate-advertisement",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER));
    }

    @Test
    @Transactional
    public void invalidateShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String INVALIDATE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + INVALIDATE_PATH;

        this.mockMvc.perform(put(INVALIDATE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void approveShouldReturnOkWhenAdvertisementExists() throws Exception {
        final String APPROVE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + APPROVE_PATH;

        this.mockMvc.perform(put(APPROVE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("approve-advertisement",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER));
    }

    @Test
    @Transactional
    public void approveShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String APPROVE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + APPROVE_PATH;

        this.mockMvc.perform(put(APPROVE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.VERIFIER_TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void doneShouldReturnOkWhenExistingAdvertisementAndCurrentUserIsAdvertiser() throws Exception {
        final String DONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + DONE_PATH;

        this.mockMvc.perform(put(DONE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("end-advertisement",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER));
    }

    @Test
    @Transactional
    public void doneShouldReturnNotFoundWhenNonExistingAdvertisement() throws Exception {
        final String DONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + DONE_PATH;

        this.mockMvc.perform(put(DONE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void doneShouldReturnNotFoundWhenCurrentUserIsNotAdvertiser() throws Exception {
        final String DONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + DONE_PATH;

        this.mockMvc.perform(put(DONE_ADVERTISEMENT, AdvertisementTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void doneShouldReturnNotFoundWhenCurrentUserIsNotAdvertiserAndNonExistingAdv() throws Exception {
        final String DONE_ADVERTISEMENT = BASE_URL + AdvertisementTestData.ID_PATH_VARIABLE + DONE_PATH;

        this.mockMvc.perform(put(DONE_ADVERTISEMENT, AdvertisementTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_ADVERTISER_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}