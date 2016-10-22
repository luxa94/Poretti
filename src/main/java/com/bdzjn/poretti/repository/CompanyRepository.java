package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findById(long id);

}
