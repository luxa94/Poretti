package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerIntegrationTest {

    private static final String URL_PREFIX = "/api/users";
    private static final String AUTHORIZATION = "X-AUTH-TOKEN";
    private static final String TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d7";

    @Autowired
    MockMvc mockMvc;

   @Test
   @Transactional
   public void createAdminShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception{
       final String CREATE_ADMIN_URL = URL_PREFIX + "/createSysAdmin";
       final RegisterDTO testEntity = testEntity();

       this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
               .header(AUTHORIZATION, TOKEN_VALUE)
               .contentType(MediaType.APPLICATION_JSON)
               .content(TestUtil.json(testEntity)))
               .andExpect(status().isCreated());
   }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenUsernameIsTaken() throws Exception{
        final String CREATE_ADMIN_URL = URL_PREFIX + "/createSysAdmin";

        final RegisterDTO testEntityWithExistingUsername = testEntity();
        testEntityWithExistingUsername.setUsername("admin");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprecessableWhenEmailIsTaken() throws Exception{
        final String CREATE_ADMIN_URL = URL_PREFIX + "/createSysAdmin";

        final RegisterDTO testEntityWithExistingEmail = testEntity();
        testEntityWithExistingEmail.setEmail("admin@admin.com");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingEmail)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnCreatedWhenUsernameOrEmailAreNotTaken() throws Exception{
        final String CREATE_VERIFIER_URL = URL_PREFIX + "/createVerifier";
        final RegisterDTO testVerifier = testEntity();

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VERIFIER_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testVerifier)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnWhenUnprocessableWhenUsernameIsTaken() throws Exception{
        final String CREATE_VERIFIER_URL = URL_PREFIX + "/createVerifier";
        final RegisterDTO testEntityWithExistingUsername = testEntity();
        testEntityWithExistingUsername.setUsername("admin");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VERIFIER_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createVerifierShouldReturnWhenUnprecessableWhenEmailIsTaken() throws Exception{
        final String CREATE_VERIFIER_URL = URL_PREFIX + "/createVerifier";
        final RegisterDTO testEntityWithExistingEmail = testEntity();
        testEntityWithExistingEmail.setEmail("admin@admin.com");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VERIFIER_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testEntityWithExistingEmail)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }


    private RegisterDTO testEntity(){
        final RegisterDTO testEntity = new RegisterDTO();
        testEntity.setEmail("test@entity.com");
        testEntity.setName("Test Entity");
        testEntity.setUsername("test_entity");
        testEntity.setPassword("test_entity");

       return testEntity;
   }

}
