package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.Company;
import com.bdzjn.poretti.repository.CompanyRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class CompanyRepositoryImpl extends QueryDslRepositorySupport implements CompanyRepositoryCustom {

    public CompanyRepositoryImpl() {
        super(Company.class);
    }

}
