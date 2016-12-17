package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AuthorizationDTO;
import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.model.User;

import java.util.Optional;

public interface AuthorizationService {

    Optional<Authorization> findByToken(String token);

    AuthorizationDTO createFor(User user);

    void deleteByToken(String token);
}
