package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.LoginDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    private static final String BASE_URL = "/api/authentication";
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";

    private static final MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testRegister() throws Exception {
        final RegisterDTO registerDTO = getRegisterDTO();

        mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(CONTENT_TYPE).content(TestUtil.json(registerDTO)))
                .andExpect(status().isCreated());

        registerDTO.setUsername("Kevin");
        mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(CONTENT_TYPE).content(TestUtil.json(registerDTO)))
                .andExpect(status().isUnprocessableEntity());

        registerDTO.setUsername("pera");
        registerDTO.setEmail("noviPera@gmail.com");
        mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(CONTENT_TYPE).content(TestUtil.json(registerDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void testLogin() throws Exception {
        final RegisterDTO registerDTO = getRegisterDTO();

        mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(CONTENT_TYPE).content(TestUtil.json(registerDTO)))
                .andExpect(status().isCreated());

        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("pera");
        loginDTO.setPassword("password");

        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(loginDTO)))
                .andExpect(status().isOk());

        loginDTO.setPassword("ups");
        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void adminLoginTest() throws Exception {
        final LoginDTO adminLogin = new LoginDTO();
        adminLogin.setUsername("admin");
        adminLogin.setPassword("admin");
        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(adminLogin)))
                .andExpect(status().isOk());
    }

    private RegisterDTO getRegisterDTO() {
        final RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("pera");
        registerDTO.setPassword("password");
        registerDTO.setEmail("pera@gmail.com");
        registerDTO.setName("Pera Peric");
        registerDTO.getContactEmails().add("pera@gmail.com");
        registerDTO.getPhoneNumbers().add("perasnumber");
        return registerDTO;
    }

}
