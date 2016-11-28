package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class UserControllerIntegrationTest {

    private static final String USERS_BASE_URL = "/api/users";

    @Autowired
    TestRestTemplate restTemplate;

   @Test
    public void createAdminShouldReturnCreatedWhenUsernameOrEmailNotTaken(){
        final String createAdminUrl = USERS_BASE_URL + "/createSysAdmin";
        final RegisterDTO testAdmin = testAdmin();
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(createAdminUrl, testAdmin, Long.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
   }


    private RegisterDTO testAdmin(){
       RegisterDTO registerAdmin = new RegisterDTO();
       registerAdmin.setEmail("admin@admin.com");
       registerAdmin.setName("Admin Senior");
       registerAdmin.setUsername("admin_senior");
       registerAdmin.setPassword("admin_senior");

       return registerAdmin;
   }

}
