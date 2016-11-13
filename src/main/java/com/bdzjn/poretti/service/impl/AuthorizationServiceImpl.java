package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.AuthorizationRepository;
import com.bdzjn.poretti.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    @Autowired
    public AuthorizationServiceImpl(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    public Optional<Authorization> findByToken(String token) {
        return authorizationRepository.findByToken(token);
    }

    /**
     * Creates an {@link com.bdzjn.poretti.model.Authorization} entity for the user who wants to log in.
     *
     * @param user that is trying to log in.
     * @return created {@link com.bdzjn.poretti.model.Authorization}.
     */
    @Override
    public Authorization createFor(User user) {
        final Authorization authorization = new Authorization();
        authorization.setUser(user);

        return authorizationRepository.save(authorization);
    }
}
