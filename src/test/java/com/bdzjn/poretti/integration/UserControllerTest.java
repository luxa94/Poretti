package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.repository.OwnerReviewRepository;
import com.bdzjn.poretti.repository.UserRepository;
import com.bdzjn.poretti.util.TestUtil;
import com.bdzjn.poretti.util.data.ReviewTestData;
import com.bdzjn.poretti.util.data.UserTestData;
import com.bdzjn.poretti.util.snippets.AuthorizationSnippets;
import com.bdzjn.poretti.util.snippets.ReviewSnippets;
import com.bdzjn.poretti.util.snippets.UserSnippets;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ActiveProfiles(profiles = "test")
public class UserControllerTest {

    private static final String BASE_URL = "/api/users";
    private static final String REAL_ESTATES_PATH = "/realEstates";
    private static final String MEMBERSHIPS_PATH = "/memberships";
    private static final String REVIEWS_PATH = "/reviews";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerReviewRepository reviewRepository;

    @Test
    @Transactional
    public void createAdminShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception {
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";
        final RegisterDTO testEntity = UserTestData.registerDTOTestEntity();
        final int numberOfElementsBefore = userRepository.findAll().size();

        this.mockMvc.perform(post(CREATE_ADMIN_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andDo(document("create-admin", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(UserSnippets.REGISTER_DTO)));

        final int numberOfElementsAfter = userRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore + 1));
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenUsernameIsTaken() throws Exception {
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";
        final RegisterDTO testEntityWithExistingUsername = UserTestData.registerDTOTestEntity();
        testEntityWithExistingUsername.setUsername("admin");
        final int numberOfElementsBefore = userRepository.findAll().size();

        this.mockMvc.perform(post(CREATE_ADMIN_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));

        final int numberOfElementsAfter = userRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenEmailIsTaken() throws Exception {
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";

        final RegisterDTO testEntityWithExistingEmail = UserTestData.registerDTOTestEntity();
        testEntityWithExistingEmail.setEmail("admin@admin.com");

        this.mockMvc.perform(post(CREATE_ADMIN_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingEmail)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception {
        final String CREATE_VERIFIER_URL = BASE_URL + "/createVerifier";
        final RegisterDTO testEntity = UserTestData.registerDTOTestEntity();
        final int numberOfElementsBefore = userRepository.findAll().size();

        this.mockMvc.perform(post(CREATE_VERIFIER_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andDo(document("create-verifier", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(UserSnippets.REGISTER_DTO)));

        final int numberOfElementsAfter = userRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore + 1));
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnUnprocessableWhenUsernameIsTaken() throws Exception {
        final String CREATE_VERIFIER_URL = BASE_URL + "/createVerifier";
        final RegisterDTO testEntityWithExistingUsername = UserTestData.registerDTOTestEntity();
        testEntityWithExistingUsername.setUsername("admin");
        final int numberOfElementsBefore = userRepository.findAll().size();

        this.mockMvc.perform(post(CREATE_VERIFIER_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));

        final int numberOfElementsAfter = userRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnUnprocessableWhenEmailIsTaken() throws Exception {
        final String CREATE_VERIFIER_URL = BASE_URL + "/createVerifier";
        final RegisterDTO testEntityWithExistingEmail = UserTestData.registerDTOTestEntity();
        testEntityWithExistingEmail.setEmail("admin@admin.com");

        this.mockMvc.perform(post(CREATE_VERIFIER_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingEmail)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenUserExists() throws Exception {
        final String FIND_ONE_USER = BASE_URL + UserTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(get(FIND_ONE_USER, UserTestData.CURRENT_USER_ID))
                .andExpect(status().isOk())
                .andDo(document("find-one-user",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID),
                        responseFields(UserSnippets.USER)));

    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_ONE_USER = BASE_URL + UserTestData.ID_PATH_VARIABLE;

        this.mockMvc.perform(get(FIND_ONE_USER, UserTestData.NON_EXISTING_ID))
                .andExpect(status().isNotFound())
                .andDo(document("find-one-user-bad-id",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID)));
    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenCurrentUserIsUserToEdit() throws Exception {
        final String EDIT_USER = BASE_URL + UserTestData.ID_PATH_VARIABLE;
        final UserDTO testEntity = UserTestData.userDTOTestEntity();

        this.mockMvc.perform(put(EDIT_USER, UserTestData.CURRENT_USER_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testEntity.getName())))
                .andExpect(jsonPath("$.imageUrl", is(testEntity.getImageUrl())))
                .andExpect(jsonPath("$.phoneNumbers", is(testEntity.getPhoneNumbers())))
                .andExpect(jsonPath("$.contactEmails", is(testEntity.getContactEmails())))
                .andDo(document("edit-user",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(UserSnippets.USER_DTO)));
    }

    @Test
    @Transactional
    public void editShouldReturnForbiddenWhenCurrentUserIsNotUserToEdit() throws Exception {
        final String EDIT_USER = BASE_URL + UserTestData.ID_PATH_VARIABLE;
        final UserDTO testEntity = UserTestData.userDTOTestEntity();

        this.mockMvc.perform(put(EDIT_USER, UserTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findRealEstatesShouldReturnOkWhenUserExists() throws Exception {
        final String FIND_REAL_ESTATES = BASE_URL + UserTestData.ID_PATH_VARIABLE + REAL_ESTATES_PATH;

        this.mockMvc.perform(get(FIND_REAL_ESTATES, UserTestData.CURRENT_USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(document("find-real-estates-of-user",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID)));
    }

    @Test
    @Transactional
    public void findRealEstatesShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_REAL_ESTATES = BASE_URL + UserTestData.ID_PATH_VARIABLE + REAL_ESTATES_PATH;

        this.mockMvc.perform(get(FIND_REAL_ESTATES, UserTestData.NON_EXISTING_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void findMembershipsShouldReturnOkWhenUserExists() throws Exception {
        //TODO: Change size with constant from user test data
        final String FIND_MEMBERSHIPS = BASE_URL + UserTestData.ID_PATH_VARIABLE + MEMBERSHIPS_PATH;

        this.mockMvc.perform(get(FIND_MEMBERSHIPS, UserTestData.CURRENT_USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(document("find-memberships-of-user",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID)));
    }

    @Test
    @Transactional
    public void findMembershipsShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_MEMBERSHIPS = BASE_URL + UserTestData.ID_PATH_VARIABLE + MEMBERSHIPS_PATH;

        this.mockMvc.perform(get(FIND_MEMBERSHIPS, UserTestData.NON_EXISTING_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnCreatedWhenTargetUserExistsAndCurrentUserIsNotTarget() throws Exception {
        final String CREATE_OWNER_REVIEW = BASE_URL + UserTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();
        final int numberOfElementsBefore = reviewRepository.findAll().size();

        this.mockMvc.perform(post(CREATE_OWNER_REVIEW, UserTestData.CURRENT_USER_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.comment", is(testEntity.getComment())))
                .andExpect(jsonPath("$.rating", is(testEntity.getRating())))
                .andExpect(jsonPath("$.target.id", is(UserTestData.CURRENT_USER_ID)))
                .andDo(document("create-review-for-user",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID),
                        AuthorizationSnippets.AUTH_HEADER,
                        requestFields(ReviewSnippets.OWNER_REVIEW)));

        final int numberOfElementsAfter = reviewRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore + 1));

    }

    @Test
    @Transactional
    public void createReviewShouldReturnNotFoundWhenNonExistingTargetUser() throws Exception {
        final String CREATE_OWNER_REVIEW = BASE_URL + UserTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_OWNER_REVIEW, UserTestData.NON_EXISTING_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnForbiddenWhenCurrentUserIsTarget() throws Exception {
        final String CREATE_OWNER_REVIEW = BASE_URL + UserTestData.ID_PATH_VARIABLE + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_OWNER_REVIEW, UserTestData.CURRENT_USER_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnOkWhenUserExists() throws Exception {
        final String FIND_REVIEWS = BASE_URL + UserTestData.ID_PATH_VARIABLE + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_REVIEWS, UserTestData.CURRENT_USER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andDo(document("find-reviews-for-user",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        pathParameters(UserSnippets.USER_ID)));
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_REVIEWS = BASE_URL + UserTestData.ID_PATH_VARIABLE + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_REVIEWS, UserTestData.NON_EXISTING_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
