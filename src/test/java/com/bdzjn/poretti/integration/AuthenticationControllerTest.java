package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.controller.dto.LoginDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    private static final String BASE_URL = "/api/authentication";
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String VERIFY = "/verify";

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
        registerDTO.setEmail("bdzjn.co@gmail.com");
        mockMvc.perform(post(BASE_URL + REGISTER)
                .contentType(CONTENT_TYPE).content(TestUtil.json(registerDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    public void loginShouldReturnOkWithValidCredentials() throws Exception {
        final LoginDTO loginDTO = UserTestData.getLoginDTO("test_user", "admin");

        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(loginDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void loginShouldReturnUnauthorizedWithWrongPassword() throws Exception {
        final LoginDTO loginDTO = UserTestData.getLoginDTO("test_user", "oops");

        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void loginShouldReturnUnauthorizedWithWrongUsername() throws Exception {
        final LoginDTO loginDTO = UserTestData.getLoginDTO("asshole", "admin");

        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void loginShouldReturnUnauthorizedWhenUserIsNotConfirmed() throws Exception {
        final LoginDTO loginDTO = UserTestData.getLoginDTO("test_test_user", "admin");

        mockMvc.perform(post(BASE_URL + LOGIN).contentType(CONTENT_TYPE).content(TestUtil.json(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void verificationShouldReturnForbiddenWhenAlreadyConfirmed() throws Exception {
        mockMvc.perform(put(BASE_URL + "/1" + VERIFY))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void verificationShouldReturnOkWhenNotConfirmed() throws Exception {
        mockMvc.perform(put(BASE_URL + "/3" + VERIFY))
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
