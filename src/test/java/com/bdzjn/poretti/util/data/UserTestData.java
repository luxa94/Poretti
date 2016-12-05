package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.RegisterDTO;

public class UserTestData {

    public static RegisterDTO registerDTOTestEntity(){
        final RegisterDTO testEntity = new RegisterDTO();
        testEntity.setEmail("test@entity.com");
        testEntity.setName("Test Entity");
        testEntity.setUsername("test_entity");
        testEntity.setPassword("test_entity");

        return testEntity;
    }

}
