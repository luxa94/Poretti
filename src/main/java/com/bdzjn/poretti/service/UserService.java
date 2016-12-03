package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(long id);

    User update(UserDTO userDTO, User user);

    boolean areUsernameOrEmailTaken(String username, String email);

    User register(RegisterDTO registerDTO);

    User createUser(RegisterDTO registerDTO);

    User createVerifier(RegisterDTO registerDTO);

    User createAdmin(RegisterDTO registerDTO);

    User login(LoginDTO loginDTO);

    void verify(long id);
}
