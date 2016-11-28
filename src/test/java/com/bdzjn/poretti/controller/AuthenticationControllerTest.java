package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.AuthorizationDTO;
import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AuthenticationControllerTest {

    private static final String URL_PREFIX = "/api/authentication";

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Rollback
    //@Transactional
    public void testRegister() throws Exception {
        final RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("pera");
        registerDTO.setPassword("password");
        registerDTO.setEmail("pera@gmail.com");
        registerDTO.setName("Pera Peric");
        registerDTO.getContactEmails().add("pera@gmail.com");
        registerDTO.getPhoneNumbers().add("perasnumber");

        final ResponseEntity successEntity = restTemplate.postForEntity(URL_PREFIX + "/register", registerDTO, Long.class);
        Assert.assertEquals(HttpStatus.CREATED, successEntity.getStatusCode());

        registerDTO.setUsername("Kevin");
        final ResponseEntity emailFailEntity = restTemplate.postForEntity(URL_PREFIX + "/register", registerDTO, String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, emailFailEntity.getStatusCode());

        registerDTO.setUsername("pera");
        registerDTO.setEmail("noviPera@gmail.com");
        final ResponseEntity usernameFailEntity = restTemplate.postForEntity(URL_PREFIX + "/register", registerDTO, String.class);
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, usernameFailEntity.getStatusCode());
    }

    @Test
   // @Transactional
    public void testLogin() throws Exception {
        final RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("pera");
        registerDTO.setPassword("password");
        registerDTO.setEmail("pera@gmail.com");
        registerDTO.setName("Pera Peric");
        registerDTO.getContactEmails().add("pera@gmail.com");
        registerDTO.getPhoneNumbers().add("perasnumber");

        final ResponseEntity successEntity = restTemplate.postForEntity(URL_PREFIX + "/register", registerDTO, Long.class);
        Assert.assertEquals(HttpStatus.CREATED, successEntity.getStatusCode());

        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("pera");
        loginDTO.setPassword("password");

        final ResponseEntity<AuthorizationDTO> responseEntity = restTemplate.postForEntity(URL_PREFIX + "/login", loginDTO, AuthorizationDTO.class);
        Assert.assertNotNull(responseEntity.getBody());
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
