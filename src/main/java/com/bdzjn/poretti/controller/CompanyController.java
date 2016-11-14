package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.CompanyDTO;
import com.bdzjn.poretti.controller.dto.RegisterCompanyDTO;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.model.Company;
import com.bdzjn.poretti.model.Membership;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.CompanyService;
import com.bdzjn.poretti.service.MembershipService;
import com.bdzjn.poretti.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final UserService userService;
    private final CompanyService companyService;
    private final MembershipService membershipService;

    @Autowired
    public CompanyController(CompanyService companyService, UserService userService, MembershipService membershipService) {
        this.userService = userService;
        this.companyService = companyService;
        this.membershipService = membershipService;
    }

    @Transactional
    @PreAuthorize("hasAuthority('CREATE_COMPANY')")
    @PostMapping
    public ResponseEntity create(@RequestBody RegisterCompanyDTO registerCompanyDTO, @AuthenticationPrincipal User admin) {
        final CompanyDTO companyDTO = registerCompanyDTO.getCompanyDTO();
        final RegisterDTO registerDTO = registerCompanyDTO.getRegisterDTO();

        if (companyDTO == null || registerDTO == null) {
            return new ResponseEntity<>("Must specify user and company.", HttpStatus.BAD_REQUEST);
        }

        if (companyService.findByPib(companyDTO.getPib()).isPresent()){
            return new ResponseEntity<>("Company with given pib already exists.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())) {
            return new ResponseEntity<>("Username or email taken.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final Company company = companyService.create(companyDTO);
        final User user = userService.createUser(registerDTO);
        final Membership membership = new Membership();
        membership.setMember(user);
        membership.setApprovedBy(admin);
        membership.setCompany(company);
        membership.setConfirmed(true);
        membershipService.create(membership);

        return new ResponseEntity(HttpStatus.CREATED);
    }

}
