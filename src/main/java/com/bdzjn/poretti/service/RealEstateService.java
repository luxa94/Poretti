package com.bdzjn.poretti.service;

import com.bdzjn.poretti.model.RealEstate;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RealEstateService {

    Optional<RealEstate> findById(long id);
}
