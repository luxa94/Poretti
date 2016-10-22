package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

    Optional<RealEstate> findById(long id);

}
