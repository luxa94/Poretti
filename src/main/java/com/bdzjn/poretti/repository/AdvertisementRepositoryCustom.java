package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Advertisement;

import java.util.Optional;

public interface AdvertisementRepositoryCustom {

    Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId);
}
