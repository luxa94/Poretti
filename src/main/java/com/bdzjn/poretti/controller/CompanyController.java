package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.*;
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
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('CREATE_COMPANY')")
    @PostMapping
    public ResponseEntity create(@RequestBody RegisterCompanyDTO registerCompanyDTO, @AuthenticationPrincipal User admin) {
        final CompanyDTO companyDTO = registerCompanyDTO.getCompanyDTO();
        final RegisterDTO registerDTO = registerCompanyDTO.getRegisterDTO();

        if (companyDTO == null || registerDTO == null) {
            return new ResponseEntity<>("Must specify user and company.", HttpStatus.BAD_REQUEST);
        }

        if (companyService.findByPib(companyDTO.getPib()).isPresent()) {
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

    @PreAuthorize("hasAnyAuthority('EDIT_COMPANY')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody CompanyDTO companyDTO,
                                 @PathVariable long id,
                                 @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}/realEstates")
    public ResponseEntity findRealEstates(@PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/realEstates")
    public ResponseEntity createRealEstate(@RequestBody RealEstateDTO realEstateDTO,
                                           @PathVariable long id,
                                           @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}/realEstates/{realEstateId}")
    public ResponseEntity editRealEstate(@RequestBody RealEstateDTO realEstateDTO,
                                         @PathVariable long id,
                                         @PathVariable long realEstateId,
                                         @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/realEstates/{realEstateId}/advertisements")
    public ResponseEntity createRealAdvertisement(@RequestBody AdvertisementDTO advertisementDTO,
                                                  @PathVariable long id,
                                                  @PathVariable long realEstateId,
                                                  @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}/advertisements")
    public ResponseEntity findAdvertisements(@PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/advertisements")
    public ResponseEntity createAdvertisementAndRealEstate(@RequestBody AdvertisementRealEstateDTO advertisementRealEstateDTO,
                                                           @PathVariable long id,
                                                           @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}/advertisements/{advertisementId}")
    public ResponseEntity editAdvertisement(@RequestBody AdvertisementDTO advertisementDTO,
                                            @PathVariable long id,
                                            @PathVariable long advertisementId,
                                            @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_REVIEW')")
    @Transactional
    @PostMapping("/{id}/reviews")
    public ResponseEntity createReview(@RequestBody ReviewDTO reviewDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}/reviews")
    public ResponseEntity findReviews(@PathVariable long id,
                                      @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}/memberships")
    public ResponseEntity findMemberships(@PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('JOIN_COMPANY')")
    @Transactional
    @PostMapping("/{id}/memberships")
    public ResponseEntity createMembership(@PathVariable long id, @AuthenticationPrincipal User user) {
        // Request for the user to join the company.
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('APPROVE_COMPANY_USER')")
    @Transactional
    @PutMapping("/{id}/memberships/{membershipId}")
    public ResponseEntity approveMembership(@PathVariable long membershipId,
                                            @PathVariable long id,
                                            @AuthenticationPrincipal User user) {
        // Should throw error if the membership is already active.
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('LEAVE_COMPANY', 'REMOVE_USER_FROM_COMPANY')")
    @Transactional
    @DeleteMapping("/{id}/memberships/{membershipId}")
    public ResponseEntity leaveCompany(@PathVariable long membershipId,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        // When the user wants to exit the company.
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

}
