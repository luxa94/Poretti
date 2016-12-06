package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.util.TestUtil;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerIntegrationTest {

    private static final String BASE_URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;

   @Test
   @Transactional
   public void createAdminShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception{
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
    public void createAdminShouldReturnWhenUnprocessableWhenUsernameIsTaken() throws Exception{
        final String CREATE_ADMIN_URL = BASE_URL + "/createSysAdmin";

        final RegisterDTO testEntityWithExistingUsername = UserTestData.registerDTOTestEntity();
        testEntityWithExistingUsername.setUsername("admin");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenEmailIsTaken() throws Exception{
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
    public void createVerifierShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception{
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
    public void createVerifierShouldReturnUnprocessableWhenUsernameIsTaken() throws Exception{
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
    public void createVerifierShouldReturnUnprocessableWhenEmailIsTaken() throws Exception{
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

}
