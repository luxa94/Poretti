package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findById(long id);

    boolean areUsernameOrEmailTaken(String username, String email);

    User create(User user);

    User register(RegisterDTO registerDTO);

    User login(LoginDTO loginDTO);

}
