package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Company;
import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.repository.MembershipRepository;
import com.bdzjn.poretti.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Optional<Membership> findById(long id) {
        return membershipRepository.findById(id);
    }

    @Override
    public Membership findActiveMembership(long memberId, long companyId) {
        return findMembership(memberId, companyId, true);
    }

    @Override
    public Membership findInactiveMembership(long memberId, long companyId) {
        return findMembership(memberId, companyId, false);
    }

    private Membership findMembership(long memberId, long companyId, boolean confirmedStatus) {
        final Membership membership = findByMemberIdAndCompanyId(memberId, companyId).orElseThrow(NotFoundException::new);
        if (membership.isConfirmed() != confirmedStatus) {
            throw new NotFoundException();
        }

        return membership;
    }

    @Override
    public Membership save(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public Membership save(User member, Company company) {
        final Membership membership = new Membership();
        membership.setMember(member);
        membership.setCompany(company);

        return membershipRepository.save(membership);
    }

    @Override
    public void delete(long id) {
        membershipRepository.delete(id);
    }

    @Override
    public Optional<Membership> findByMemberIdAndCompanyId(long memberId, long companyId) {
        return membershipRepository.findByMemberIdAndCompanyId(memberId, companyId);
    }
}
