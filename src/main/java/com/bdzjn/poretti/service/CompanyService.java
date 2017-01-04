package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.CompanyDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company create(CompanyDTO companyDTO);

    Optional<Company> findByPib(String pib);

    Optional<Company> findById(long id);

    /**
     * Edits company with the given id.
     * In case when there is no company with this id {@link NotFoundException} is thrown.
     *
     * @param id
     * @param companyDTO
     */
    void edit(long id, CompanyDTO companyDTO);

    List<Company> findAll();
}
