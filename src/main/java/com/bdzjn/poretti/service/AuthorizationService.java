package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AuthorizationDTO;
import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.model.User;

import java.util.Optional;

public interface AuthorizationService {

    /**
     *
     * @param token Token for finding Authorization
     * @return {@link Authorization}
     */
    Optional<Authorization> findByToken(String token);

    /**
     * Creates {@link Authorization} token for user who wants to log in.
     *
     * @param user User for whom new {@link Authorization} is created
     * @return DTO object based on created authorization
     */
    AuthorizationDTO createFor(User user);

    void deleteByToken(String token);
}
