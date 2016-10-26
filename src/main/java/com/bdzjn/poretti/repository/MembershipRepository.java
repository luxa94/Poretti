package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MembershipRepository extends JpaRepository<Membership, Long>, MembershipRepositoryCustom {

    Optional<Membership> findById(long id);
}
