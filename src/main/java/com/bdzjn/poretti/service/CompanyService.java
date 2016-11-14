package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.CompanyDTO;
import com.bdzjn.poretti.model.Company;

import java.util.Optional;

public interface CompanyService {

    Company create(CompanyDTO companyDTO);

    Optional<Company> findByPib(String pib);
}
