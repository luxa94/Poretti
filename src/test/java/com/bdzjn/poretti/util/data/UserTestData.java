package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;

import java.util.ArrayList;

public class UserTestData {

    public static final String AUTHORIZATION = "X-AUTH-TOKEN";
    public static final String TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d8";
    public static final String ADMIN_TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26d7";
    public static final String VERIFIER_TOKEN_VALUE = "102da414-847d-4602-8b2d-edca26ab26e7";
    public static final String NOT_OWNER_TOKEN = "102da414-847d-4602-8b2d-edca26ab26d9";
    public static final String NOT_ADVERTISER_TOKEN = NOT_OWNER_TOKEN;
    public static final String CURRENT_USER_ID_PATH = "/2";
    public static final String NON_EXISTING_ID_PATH = "/100";
    public static final int CURRENT_USER_ID = 2;

    public static RegisterDTO registerDTOTestEntity() {
        final RegisterDTO testEntity = new RegisterDTO();
        testEntity.setEmail("test@entity.com");
        testEntity.setName("Test Entity");
        testEntity.setUsername("test_entity");
        testEntity.setPassword("test_entity");

        return testEntity;
    }

    public static LoginDTO getLoginDTO(String username, String password) {
        final LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);
        return loginDTO;
    }

    public static UserDTO userDTOTestEntity() {
        final UserDTO testEntity = new UserDTO();
        testEntity.setName("New name");
        testEntity.setContactEmails(new ArrayList<>());
        testEntity.setPhoneNumbers(new ArrayList<>());
        testEntity.setImageUrl("/images/newUserImage.jpg");

        return testEntity;
    }

}
