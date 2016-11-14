package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.repository.MembershipRepository;
import com.bdzjn.poretti.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Membership create(Membership membership) {
        return membershipRepository.save(membership);
    }
}
