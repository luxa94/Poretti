package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    Optional<Authorization> findByToken(String token);
}
