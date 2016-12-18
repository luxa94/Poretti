package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.AdvertisementTestData;
import com.bdzjn.poretti.util.data.RealEstateTestData;
import com.bdzjn.poretti.util.data.UserTestData;
import com.bdzjn.poretti.util.snippets.AdvertisementSnippets;
import com.bdzjn.poretti.util.snippets.AuthorizationSnippets;
import com.bdzjn.poretti.util.snippets.RealEstateSnippets;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ActiveProfiles(profiles = "test")
public class RealEstateControllerTest {

    private static final String BASE_URL = "/api/realEstates";
    private static final String ADVERTISEMENTS_PATH = "/advertisements";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenRealEstateExists() throws Exception {
        final String FIND_ONE_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(get(FIND_ONE_REAL_ESTATE, RealEstateTestData.EXISTING_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(RealEstateTestData.EXISTING_ID)))
                .andExpect(jsonPath("$.name", is(RealEstateTestData.EXISTING_NAME)))
                .andExpect(jsonPath("$.description", is(RealEstateTestData.EXISTING_DESCRIPTION)))
                .andExpect(jsonPath("$.area", is(RealEstateTestData.EXISTING_AREA)))
                .andExpect(jsonPath("$.location.id", is(RealEstateTestData.LOCATION_ID)))
                .andExpect(jsonPath("$.imageUrl", is(RealEstateTestData.EXISTING_IMAGE)))
                .andExpect(jsonPath("$.type", is(RealEstateTestData.EXISTING_TYPE)))
                .andExpect(jsonPath("$.owner.id", is(RealEstateTestData.OWNER_ID)))
                .andDo(document("find-one-real-estate", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Id of real estate to be found")
                        ),
                        responseFields(
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").description("Name of real estate"),
                                fieldWithPath("description").description("Description of real estate"),
                                fieldWithPath("area").description("Area of real estate"),
                                fieldWithPath("location").description("Location of real estate"),
                                fieldWithPath("imageUrl").description("Image of real estate"),
                                fieldWithPath("type").description("Type of real estate"),
                                fieldWithPath("owner").description("Owner of real estate"),
                                fieldWithPath("technicalEquipment").description("Technical equipment of real estate")
                        )));
    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String FIND_ONE_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(get(FIND_ONE_REAL_ESTATE, RealEstateTestData.NON_EXISTING_ID))
                .andExpect(status().isNotFound())
                .andDo(document("find-one-real-estate"));
    }

    @Test
    @Transactional
    public void createShouldReturnCreated() throws Exception {
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(post(BASE_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
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
                .andExpect(jsonPath("$.technicalEquipment", hasSize(testEntity.getTechnicalEquipment().size())))
                .andExpect(jsonPath("$.owner.id", is(UserTestData.CURRENT_USER_ID)))
                .andDo(document("create-real-estate", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(RealEstateSnippets.REAL_ESTATE_DTO)));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenRealEstateExistsAndCurrentUserIsOwner() throws Exception {
        final String EDIT_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        testEntity.setName("Real estate new test name");

        this.mockMvc.perform(put(EDIT_REAL_ESTATE, RealEstateTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
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
                .andExpect(jsonPath("$.technicalEquipment", hasSize(testEntity.getTechnicalEquipment().size())))
                .andExpect(jsonPath("$.owner.id", is(UserTestData.CURRENT_USER_ID)))
                .andDo(document("edit-real-estate", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(RealEstateSnippets.REAL_ESTATE_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(RealEstateSnippets.REAL_ESTATE_DTO)));
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String EDIT_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(put(EDIT_REAL_ESTATE, RealEstateTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotOwner() throws Exception {
        final String EDIT_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(put(EDIT_REAL_ESTATE, RealEstateTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void editShouldReturnNotFoundWhenCurrentUserIsNotOwnerAndNonExistingRealEstate() throws Exception {
        final String EDIT_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();

        this.mockMvc.perform(put(EDIT_REAL_ESTATE, RealEstateTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenRealEstateExistsAndCurrentUserIsOwner() throws Exception {
        final String DELETE_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(delete(DELETE_REAL_ESTATE, RealEstateTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andExpect(status().isOk())
                .andDo(document("delete-real-estate", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(AdvertisementSnippets.ADVERTISEMENT_ID),
                        AuthorizationSnippets.AUTH_HEADER));
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String DELETE_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(delete(DELETE_REAL_ESTATE, RealEstateTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotOwner() throws Exception {
        final String DELETE_REAL_ESTATE = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(delete(DELETE_REAL_ESTATE, RealEstateTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void findAdvertisementsShouldReturnOkWhenRealEstateExists() throws Exception {
        final String FIND_REAL_ESTATE_ADVERTISEMENTS = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE + ADVERTISEMENTS_PATH;

        this.mockMvc.perform(get(FIND_REAL_ESTATE_ADVERTISEMENTS, RealEstateTestData.EXISTING_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(RealEstateTestData.OCCURRENCE_IN_ADVERTISEMENTS)))
                .andDo(document("find-advertisements-of-real-estate",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        responseFields(fieldWithPath("[]").description("An array of advertisements"))
                                .andWithPrefix("[].", AdvertisementSnippets.ADVERTISEMENT)));
    }

    @Test
    @Transactional
    public void findAdvertisementsShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String FIND_REAL_ESTATE_ADVERTISEMENTS = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE + ADVERTISEMENTS_PATH;

        this.mockMvc.perform(get(FIND_REAL_ESTATE_ADVERTISEMENTS, RealEstateTestData.NON_EXISTING_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnCreatedWhenRealEstateExistsAndCurrentUserIsOwner() throws Exception {
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE + ADVERTISEMENTS_PATH;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT, RealEstateTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.title", is(testEntity.getTitle())))
                .andExpect(jsonPath("$.advertiser.id", is(UserTestData.CURRENT_USER_ID)))
                .andExpect(jsonPath("$.status", is(AdvertisementStatus.ACTIVE.toString())))
                .andExpect(jsonPath("$.type", is(testEntity.getType().toString())))
                .andExpect(jsonPath("$.price", is(testEntity.getPrice())))
                .andExpect(jsonPath("$.currency", is(testEntity.getCurrency().toString())))
                .andExpect(jsonPath("$.realEstate.id", is(RealEstateTestData.EXISTING_ID)))
                .andDo(document("create-advertisement-for-real-estate", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(RealEstateSnippets.REAL_ESTATE_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(AdvertisementSnippets.ADVERTISEMENT_DTO)));
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnNotFoundWhenNonExistingRealEstate() throws Exception {
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE + ADVERTISEMENTS_PATH;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT, RealEstateTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnNotFoundWhenCurrentUserIsNotOwner() throws Exception {
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE + ADVERTISEMENTS_PATH;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT, RealEstateTestData.EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createAdvertisementShouldReturnNotFoundWhenCurrentIsNotOwnerAndNonExistingRE() throws Exception {
        final String CREATE_REAL_ESTATE_ADVERTISEMENT = BASE_URL + RealEstateTestData.ID_PATH_VARIABLE + ADVERTISEMENTS_PATH;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();

        this.mockMvc.perform(post(CREATE_REAL_ESTATE_ADVERTISEMENT, RealEstateTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

}
