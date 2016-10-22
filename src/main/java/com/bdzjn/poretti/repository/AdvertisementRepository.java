package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findById(long id);

}
