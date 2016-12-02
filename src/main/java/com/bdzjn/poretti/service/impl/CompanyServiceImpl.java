package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.dto.CompanyDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
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
        company.setLocation(companyDTO.getLocation());
        company.setPhoneNumbers(companyDTO.getPhoneNumbers());
        company.setContactEmails(companyDTO.getContactEmails());

        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findByPib(String pib) {
        return companyRepository.findByPib(pib);
    }

    @Override
    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public void edit(long id, CompanyDTO companyDTO) {
        final Company company = companyRepository.findById(id).orElseThrow(NotFoundException::new);
        company.setPhoneNumbers(companyDTO.getPhoneNumbers());
        company.setContactEmails(companyDTO.getContactEmails());
        company.setImageUrl(companyDTO.getImageUrl());
        company.setLocation(companyDTO.getLocation());
        company.setName(companyDTO.getName());

        companyRepository.save(company);
    }

}
