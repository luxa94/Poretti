package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Company;
import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.model.User;

import java.util.Optional;

public interface MembershipService {

    Optional<Membership> findById(long id);

    /**
     * Finds active membership for specified member in company.
     * In case if member doesn't belong to the company {@link NotFoundException} is thrown.
     * If case if membership is not confirmed {@link NotFoundException} is thrown.
     *
     * @param memberId
     * @param companyId
     * @return {@link Membership} Link between Member and {@link Company}
     */
    Membership findActiveMembership(long memberId, long companyId);

    /**
     * Finds inactive membership for specified member in company.
     * In case if member doesn't belong to the company {@link NotFoundException} is thrown.
     * If case if membership is confirmed {@link NotFoundException} is thrown.
     *
     * @param memberId
     * @param companyId
     * @return {@link Membership} Link between Member and {@link Company}
     */
    Membership findInactiveMembership(long memberId, long companyId);

    Optional<Membership> findByMemberIdAndCompanyId(long memberId, long companyId);

    Membership save(Membership membership);

    Membership save(User member, Company company);

    void delete(long id);
}
