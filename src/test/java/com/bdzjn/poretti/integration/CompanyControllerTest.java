package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CompanyControllerTest {

    private static final String BASE_URL = "/api/companies";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findOneShouldReturnOkWhenCompanyExists() throws Exception {
        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void findOneShouldReturnNotFoundWhenNonExistingCompany() throws Exception {
        this.mockMvc.perform(get(BASE_URL + "/1232"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createShouldReturnCreatedWithNotExistingUniqueData() throws Exception {
        final RegisterCompanyDTO registerCompanyDTO = CompanyTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .content(TestUtil.json(registerCompanyDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(CompanyTestData.COMPANY_ADMIN_FREE_USERNAME);
        loginDTO.setPassword(CompanyTestData.COMPANY_ADMIN_PASSWORD);

        this.mockMvc.perform(post("/api/authentication/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(loginDTO)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWithExistingPib() throws Exception {
        final RegisterCompanyDTO registerCompanyDTO = CompanyTestData.testEntity();
        registerCompanyDTO.getCompanyDTO().setPib(CompanyTestData.EXISTING_PIB);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .content(TestUtil.json(registerCompanyDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWithExistingUsername() throws Exception {
        final RegisterCompanyDTO registerCompanyDTO = CompanyTestData.testEntity();
        registerCompanyDTO.getRegisterDTO().setUsername(UserTestData.EXISTING_USERNAME);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .content(TestUtil.json(registerCompanyDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWithExistingEmail() throws Exception {
        final RegisterCompanyDTO registerCompanyDTO = CompanyTestData.testEntity();
        registerCompanyDTO.getRegisterDTO().setEmail(UserTestData.EXISTING_EMAIL);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .content(TestUtil.json(registerCompanyDTO)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void editShouldReturnOkWithConfirmedMember() throws Exception {
        final CompanyDTO companyDTO = CompanyTestData.getCompanyDTO();

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .content(TestUtil.json(companyDTO)))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name", is(CompanyTestData.TEST_COMPANY_NAME)))
                .andExpect(jsonPath("$.location.city", is(CompanyTestData.TEST_COMPANY_CITY)));
    }

    @Test
    public void editShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        final CompanyDTO companyDTO = CompanyTestData.getCompanyDTO();

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .content(TestUtil.json(companyDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createRealEstateShouldReturnCreatedWhenConfirmedMember() throws Exception {
        final RealEstateDTO realEstateDTO = RealEstateTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(realEstateDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createRealEstateShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        final RealEstateDTO realEstateDTO = RealEstateTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(realEstateDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void editRealEstateShouldReturnOkWhenConfirmedMember() throws Exception {
        final RealEstateDTO realEstateDTO = CompanyTestData.editedRealEstate();

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH + CompanyTestData.EXISTING_COMPANY_REAL_ESTATE_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(realEstateDTO)))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/realEstates/2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is(CompanyTestData.EDITED_REAL_ESTATE_DESCRIPTION)))
                .andExpect(jsonPath("$.technicalEquipment", hasSize(2)))
                .andExpect(jsonPath("$.area", is(CompanyTestData.EDITED_AREA)));
    }

    @Test
    public void editRealEstateShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        final RealEstateDTO realEstateDTO = CompanyTestData.editedRealEstate();

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH + CompanyTestData.EXISTING_COMPANY_REAL_ESTATE_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(realEstateDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteRealEstateShouldReturnOkWhenConfirmedMember() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH + CompanyTestData.EXISTING_COMPANY_REAL_ESTATE_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteRealEstateShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH + CompanyTestData.EXISTING_COMPANY_REAL_ESTATE_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void createAdvertisementForRealEstateShouldReturnCreatedWhenConfirmedMember() throws Exception {
        final AdvertisementDTO advertisementDTO = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH + CompanyTestData.EXISTING_COMPANY_REAL_ESTATE_ID + CompanyTestData.ADVERTISEMENTS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(advertisementDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void createAdvertisementForRealEstateShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        final AdvertisementDTO advertisementDTO = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH + CompanyTestData.EXISTING_COMPANY_REAL_ESTATE_ID + CompanyTestData.ADVERTISEMENTS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(advertisementDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void createAdvertisementAndRealEstateShouldReturnCreatedWhenConfirmedMember() throws Exception {
        final AdvertisementRealEstateDTO advertisementRealEstateDTO = AdvertisementTestData.realEstateAdvertisementTestEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(advertisementRealEstateDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createAdvertisementAndRealEstateShouldReturnNotFoundWhenConfirmedMember() throws Exception {
        final AdvertisementRealEstateDTO advertisementRealEstateDTO = AdvertisementTestData.realEstateAdvertisementTestEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(advertisementRealEstateDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REAL_ESTATES_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void editAdvertisementShouldReturnOkWhenConfirmedMember() throws Exception {
        final AdvertisementDTO advertisementDTO = CompanyTestData.editedAdvertisementDTO();

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH + CompanyTestData.EXISTING_COMPANY_ADVERTISEMENT_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .content(TestUtil.json(advertisementDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is(AdvertisementType.RENT.name())))
                .andExpect(jsonPath("$.title", is(CompanyTestData.EDITED_ADVERTISEMENT_TITLE)))
                .andExpect(jsonPath("$.price", is(CompanyTestData.EDITED_ADVERTISEMENT_PRICE)));
    }

    @Test
    public void editAdvertisementShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        final AdvertisementDTO advertisementDTO = CompanyTestData.editedAdvertisementDTO();

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH + CompanyTestData.EXISTING_COMPANY_ADVERTISEMENT_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .content(TestUtil.json(advertisementDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAdvertisementShouldReturnOkWhenConfirmedMember() throws Exception {
        final ReviewDTO reviewDTO = ReviewTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REVIEWS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(reviewDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(delete(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH + CompanyTestData.EXISTING_COMPANY_ADVERTISEMENT_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
    }

    @Test
    public void deleteAdvertisementShouldReturnNotFoundWhenNonConfirmedMember() throws Exception {
        this.mockMvc.perform(delete(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH + CompanyTestData.EXISTING_COMPANY_ADVERTISEMENT_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.ADVERTISEMENTS_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void createReviewShouldReturnOkWhenCompanyExists() throws Exception {
        final ReviewDTO reviewDTO = ReviewTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REVIEWS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(reviewDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REVIEWS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(reviewDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.REVIEWS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createReviewShouldReturnNotFoundWhenCompanyDoesNotExist() throws Exception {
        final ReviewDTO reviewDTO = ReviewTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.NON_EXISTING_COMPANY_ID + CompanyTestData.REVIEWS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(reviewDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(post(BASE_URL + CompanyTestData.NON_EXISTING_COMPANY_ID + CompanyTestData.REVIEWS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(reviewDTO)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void companyShouldHaveTwoMemberships() throws Exception {
        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createMembershipShouldReturnForbiddenWhenAlreadyMember() throws Exception {
        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createMembershipShouldReturnForbiddenWhenMembershipExists() throws Exception {
        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void createMembershipShouldReturnCreatedWhenMembershipDoesNotExist() throws Exception {
        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NO_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void approveMembershipShouldReturnOkWhenConfirmedMember() throws Exception {
        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH + CompanyTestData.UNCONFIRMED_MEMBERSHIP_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].confirmed", everyItem(is(true))));
    }

    @Test
    public void approveMembershipShouldReturnNotFoundWhenApprovingSelf() throws Exception {
        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH + CompanyTestData.UNCONFIRMED_MEMBERSHIP_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void approveMembershipShouldReturnNotFoundWhenMemberNotConfirmed() throws Exception {
        this.mockMvc.perform(post(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NO_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH + CompanyTestData.UNCONFIRMED_MEMBERSHIP_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NO_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void approveMembershipShouldReturnConflictWhenMembershipAlreadyActive() throws Exception {
        this.mockMvc.perform(put(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH + CompanyTestData.CONFIRMED_MEMBERSHIP_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void deleteMembershipShouldReturnOkWhenDeletingOwnConfirmed() throws Exception {
        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));

        this.mockMvc.perform(delete(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH + CompanyTestData.CONFIRMED_MEMBERSHIP_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

//        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$", hasSize(1))); // FIXME
    }

    @Test
    public void deleteMembershipShouldReturnOkWhenDeletingOwnNotConfirmed() throws Exception {
        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));

        this.mockMvc.perform(delete(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH + CompanyTestData.UNCONFIRMED_MEMBERSHIP_ID)
                .header(UserTestData.AUTHORIZATION, CompanyTestData.USER_NOT_CONFIRMED_MEMBERSHIP)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

//        this.mockMvc.perform(get(BASE_URL + CompanyTestData.EXISTING_COMPANY_ID + CompanyTestData.MEMBERSHIPS_PATH)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$", hasSize(1))); // FIXME
    }

}
