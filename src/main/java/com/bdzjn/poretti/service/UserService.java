package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.LoginDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.controller.exception.AuthenticationException;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
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

    /**
     * Checks if username and password are good.
     * In case when user with given username doesn't exist {@link AuthenticationException} is thrown.
     * In case when user didn't verified his account {@link AuthenticationException} is thrown.
     *
     * @param loginDTO
     * @return
     */
    User login(LoginDTO loginDTO);

    /**
     * Verifies account of user with the given id.
     * In case when user doesn't exist {@link NotFoundException} is thrown.
     * In case when user have already verified his account {@link ForbiddenException} is thrown.
     * @param id
     */
    void verify(long id);
}
