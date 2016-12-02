package com.bdzjn.poretti.service;

import com.bdzjn.poretti.model.Company;
import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.model.User;

import java.util.Optional;

public interface MembershipService {

    Optional<Membership> findById(long id);

    Membership findActiveMembership(long memberId, long companyId);

    Membership findInactiveMembership(long memberId, long companyId);

    Optional<Membership> findByMemberIdAndCompanyId(long memberId, long companyId);

    Membership save(Membership membership);

    Membership save(User member, Company company);

    void delete(long id);
}
