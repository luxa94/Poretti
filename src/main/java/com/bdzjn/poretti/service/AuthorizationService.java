package com.bdzjn.poretti.service;

import com.bdzjn.poretti.model.Authorization;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.AuthorizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizationService {

    private final AuthorizationRepository authorizationRepository;

    @Autowired
    public AuthorizationService(AuthorizationRepository authorizationRepository) {
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
    public Authorization createFor(User user) {
        final Authorization authorization = new Authorization();
        authorization.setUser(user);
        authorizationRepository.save(authorization);

        return authorization;
    }

}
