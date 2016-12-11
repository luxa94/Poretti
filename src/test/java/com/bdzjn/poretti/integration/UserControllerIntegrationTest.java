package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.util.TestUtil;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerIntegrationTest {

    private static final String BASE_URL = "/api/users";
    private static final String REAL_ESTATES_PATH = "/realEstates";
    private static final String MEMBERSHIPS_PATH = "/memberships";
    private static final String REVIEWS_PATH = "/reviews";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void createAdminShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception {
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";
        final RegisterDTO testEntity = UserTestData.registerDTOTestEntity();

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenUsernameIsTaken() throws Exception {
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";

        final RegisterDTO testEntityWithExistingUsername = UserTestData.registerDTOTestEntity();
        testEntityWithExistingUsername.setUsername("admin");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenEmailIsTaken() throws Exception {
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";

        final RegisterDTO testEntityWithExistingEmail = UserTestData.registerDTOTestEntity();
        testEntityWithExistingEmail.setEmail("admin@admin.com");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
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

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VERIFIER_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnUnprocessableWhenUsernameIsTaken() throws Exception {
        final String CREATE_VERIFIER_URL = BASE_URL + "/createVerifier";
        final RegisterDTO testEntityWithExistingUsername = UserTestData.registerDTOTestEntity();
        testEntityWithExistingUsername.setUsername("admin");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VERIFIER_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnUnprocessableWhenEmailIsTaken() throws Exception {
        final String CREATE_VERIFIER_URL = BASE_URL + "/createVerifier";
        final RegisterDTO testEntityWithExistingEmail = UserTestData.registerDTOTestEntity();
        testEntityWithExistingEmail.setEmail("admin@admin.com");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VERIFIER_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.ADMIN_TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingEmail)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void findOneShouldReturnOkWhenUserExists() throws Exception {

    }

    @Test
    @Transactional
    public void findOneShouldReturnNotFoundWhenNonExistingUser() throws Exception {

    }

    @Test
    @Transactional
    public void editShouldReturnOkWhenCurrentUserIsUserToEdit() throws Exception {
        final String EDIT_USER = BASE_URL + UserTestData.CURRENT_USER_ID_PATH;
        UserDTO testEntity = UserTestData.userDTOTestEntity();

        this.mockMvc.perform(put(EDIT_USER)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testEntity.getName())))
                .andExpect(jsonPath("$.imageUrl", is(testEntity.getImageUrl())))
                .andExpect(jsonPath("$.phoneNumbers", is(testEntity.getPhoneNumbers())))
                .andExpect(jsonPath("$.contactEmails", is(testEntity.getContactEmails())));
    }

    @Test
    @Transactional
    public void editShouldReturnForbiddenWhenCurrentUserIsNotUserToEdit() throws Exception {
        final String EDIT_USER = BASE_URL + UserTestData.CURRENT_USER_ID_PATH;
        UserDTO testEntity = UserTestData.userDTOTestEntity();

        this.mockMvc.perform(put(EDIT_USER)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findRealEstatesShouldReturnOkWhenUserExists() throws Exception {
        final String FIND_REAL_ESTATES = BASE_URL + UserTestData.CURRENT_USER_ID_PATH + REAL_ESTATES_PATH;

        this.mockMvc.perform(get(FIND_REAL_ESTATES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional
    public void findRealEstatesShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_REAL_ESTATES = BASE_URL + UserTestData.NON_EXISTING_ID_PATH + REAL_ESTATES_PATH;

        this.mockMvc.perform(get(FIND_REAL_ESTATES))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void findMembershipsShouldReturnOkWhenUserExists() throws Exception {
        //TODO: Change size with constant from user test data
        final String FIND_MEMBERSHIPS = BASE_URL + UserTestData.CURRENT_USER_ID_PATH + MEMBERSHIPS_PATH;

        this.mockMvc.perform(get(FIND_MEMBERSHIPS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void findMembershipsShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_MEMBERSHIPS = BASE_URL + UserTestData.NON_EXISTING_ID_PATH + MEMBERSHIPS_PATH;

        this.mockMvc.perform(get(FIND_MEMBERSHIPS))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnCreatedWhenTargetUserExistsAndCurrentUserIsNotTarget() throws Exception {
        final String CREATE_OWNER_REVIEW = BASE_URL + UserTestData.CURRENT_USER_ID_PATH + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_OWNER_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.comment", is(testEntity.getComment())))
                .andExpect(jsonPath("$.rating", is(testEntity.getRating())))
                .andExpect(jsonPath("$.target.id", is(UserTestData.CURRENT_USER_ID)));
    }

    @Test
    @Transactional
    public void createReviewShouldReturnNotFoundWhenNNonExistingTargetUser() throws Exception {
        final String CREATE_OWNER_REVIEW = BASE_URL + UserTestData.NON_EXISTING_ID_PATH + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_OWNER_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void createReviewShouldReturnForbiddenWhenCurrentUserIsTarget() throws Exception {
        final String CREATE_OWNER_REVIEW = BASE_URL + UserTestData.CURRENT_USER_ID_PATH + REVIEWS_PATH;
        final ReviewDTO testEntity = ReviewTestData.testEntity();

        this.mockMvc.perform(post(CREATE_OWNER_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntity)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnOkWhenUserExists() throws Exception {
        final String FIND_REVIEWS = BASE_URL + UserTestData.CURRENT_USER_ID_PATH + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_REVIEWS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @Transactional
    public void findReviewsShouldReturnNotFoundWhenNonExistingUser() throws Exception {
        final String FIND_REVIEWS = BASE_URL + UserTestData.NON_EXISTING_ID_PATH + REVIEWS_PATH;

        this.mockMvc.perform(get(FIND_REVIEWS))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
