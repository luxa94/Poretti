package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>, AdvertisementRepositoryCustom {

    Optional<Advertisement> findById(long id);

    Page<Advertisement> findByStatusAndAdvertiserId(AdvertisementStatus status, long advertiserId, Pageable pageable);

}
