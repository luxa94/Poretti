package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.CompanyDTO;
import com.bdzjn.poretti.model.Company;
import com.bdzjn.poretti.repository.CompanyRepository;
import com.bdzjn.poretti.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company create(CompanyDTO companyDTO) {
        final Company company = new Company();
        company.setPib(companyDTO.getPib());
        company.setName(companyDTO.getName());
        company.setImageUrl(companyDTO.getImageUrl());
        company.setPhoneNumbers(companyDTO.getPhoneNumbers());
        company.setContactEmails(companyDTO.getContactEmails());

        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findByPib(String pib) {
        return companyRepository.findByPib(pib);
    }

}
