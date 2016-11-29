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
       final RegisterDTO testAdmin = testEntityForCreateAdmin();

       this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
               .header(AUTHORIZATION, TOKEN_VALUE)
               .contentType(MediaType.APPLICATION_JSON)
               .content(TestUtil.json(testAdmin)))
               .andExpect(status().isCreated());
   }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprocessableWhenUsernameIsTaken() throws Exception{
        final String CREATE_ADMIN_URL = URL_PREFIX + "/createSysAdmin";

        final RegisterDTO testAdminWithExistingUsername = testEntityForCreateAdmin();
        testAdminWithExistingUsername.setUsername("admin");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testAdminWithExistingUsername)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    @Test
    @Transactional
    public void createAdminShouldReturnWhenUnprecessableWhenEmailIsTaken() throws Exception{
        final String CREATE_ADMIN_URL = URL_PREFIX + "/createSysAdmin";

        final RegisterDTO testAdminWithExistingEmail = testEntityForCreateAdmin();
        testAdminWithExistingEmail.setEmail("ja");

        this.mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ADMIN_URL)
                .header(AUTHORIZATION, TOKEN_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.json(testAdminWithExistingEmail)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Username or email taken."));
    }

    private RegisterDTO testEntityForCreateAdmin(){
       final RegisterDTO registerAdmin = new RegisterDTO();
       registerAdmin.setEmail("newadmin@admin.com");
       registerAdmin.setName("Admin Senior");
       registerAdmin.setUsername("admin_senior");
       registerAdmin.setPassword("admin_senior");

       return registerAdmin;
   }

}
