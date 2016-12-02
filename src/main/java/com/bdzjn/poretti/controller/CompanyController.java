package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.*;
import com.bdzjn.poretti.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final UserService userService;
    private final CompanyService companyService;
    private final MembershipService membershipService;
    private final RealEstateService realEstateService;
    private final OwnerReviewService ownerReviewService;
    private final AdvertisementService advertisementService;

    @Autowired
    public CompanyController(CompanyService companyService, UserService userService, MembershipService membershipService, RealEstateService realEstateService, OwnerReviewService ownerReviewService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.companyService = companyService;
        this.membershipService = membershipService;
        this.realEstateService = realEstateService;
        this.ownerReviewService = ownerReviewService;
        this.advertisementService = advertisementService;
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(company, HttpStatus.OK);
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
        membershipService.save(membership);

        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_COMPANY')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody CompanyDTO companyDTO,
                               @PathVariable long id,
                               @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);

        companyService.edit(id, companyDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/realEstates")
    public ResponseEntity createRealEstate(@RequestBody RealEstateDTO realEstateDTO,
                                           @PathVariable long id,
                                           @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        final RealEstate realEstate = realEstateService.create(realEstateDTO, company);

        return new ResponseEntity<>(realEstate, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/realEstates")
    public ResponseEntity findRealEstates(@PathVariable long id) {
        // TODO: Paging, sorting, filtering.
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        final List<RealEstate> realEstates = company.getRealEstates();
        return new ResponseEntity<>(realEstates, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}/realEstates/{realEstateId}")
    public ResponseEntity editRealEstate(@RequestBody RealEstateDTO realEstateDTO,
                                         @PathVariable long id,
                                         @PathVariable long realEstateId,
                                         @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        realEstateDTO.setId(realEstateId);
        final RealEstate realEstate = realEstateService.edit(realEstateDTO, id);

        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/realEstates/{realEstateId}/advertisements")
    public ResponseEntity createAdvertisement(@RequestBody AdvertisementDTO advertisementDTO,
                                              @PathVariable long id,
                                              @PathVariable long realEstateId,
                                              @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        final RealEstate realEstate = realEstateService.findByIdAndOwnerId(realEstateId, id).orElseThrow(NotFoundException::new);
        final Advertisement advertisement = advertisementService.create(advertisementDTO, realEstate);

        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/advertisements")
    public ResponseEntity findAdvertisements(@PathVariable long id) {
        // TODO: Paging, sorting, filtering.
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        final List<Advertisement> advertisements = company.getAdvertisements();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/advertisements")
    public ResponseEntity createAdvertisementAndRealEstate(@RequestBody AdvertisementRealEstateDTO advertisementRealEstateDTO,
                                                           @PathVariable long id,
                                                           @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);

        final AdvertisementDTO advertisementDTO = advertisementRealEstateDTO.getAdvertisementDTO();
        final RealEstateDTO realEstateDTO = advertisementRealEstateDTO.getRealEstateDTO();

        final RealEstate realEstate = realEstateService.create(realEstateDTO, company);
        final Advertisement advertisement = advertisementService.create(advertisementDTO, realEstate);
        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}/advertisements/{advertisementId}")
    public ResponseEntity editAdvertisement(@RequestBody AdvertisementDTO advertisementDTO,
                                            @PathVariable long id,
                                            @PathVariable long advertisementId,
                                            @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        advertisementDTO.setId(advertisementId);
        final Advertisement advertisement = advertisementService.edit(advertisementDTO, id);

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_REVIEW')")
    @Transactional
    @PostMapping("/{id}/reviews")
    public ResponseEntity createReview(@RequestBody ReviewDTO reviewDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        final OwnerReview ownerReview = ownerReviewService.create(reviewDTO, company, user);
        return new ResponseEntity<>(ownerReview, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/reviews")
    public ResponseEntity findReviews(@PathVariable long id) {
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        final List<OwnerReview> reviews = company.getReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/memberships")
    public ResponseEntity findMemberships(@PathVariable long id) {
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        final List<Membership> memberships = company.getMemberships();
        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('JOIN_COMPANY')")
    @Transactional
    @PostMapping("/{id}/memberships")
    public ResponseEntity createMembership(@PathVariable long id, @AuthenticationPrincipal User user) {
        // Request for the user to join the company.
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        membershipService.findByMemberIdAndCompanyId(user.getId(), id).ifPresent(membership -> {
            throw new ForbiddenException("Request already pending for approval or accepted.");
        });

        final Membership membership = membershipService.save(user, company);
        return new ResponseEntity<>(membership, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('APPROVE_COMPANY_USER')")
    @Transactional
    @PutMapping("/{id}/memberships/{membershipId}")
    public ResponseEntity approveMembership(@PathVariable long membershipId,
                                            @PathVariable long id,
                                            @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        final Membership membership = membershipService.findById(membershipId).orElseThrow(NotFoundException::new);
        if (membership.isConfirmed()) {
            return new ResponseEntity<>("Already accepted.", HttpStatus.CONFLICT);
        }

        membership.setConfirmed(true);
        membership.setApprovedBy(user);
        membershipService.save(membership);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LEAVE_COMPANY', 'REMOVE_USER_FROM_COMPANY')")
    @Transactional
    @DeleteMapping("/{id}/memberships/{membershipId}")
    public ResponseEntity leaveCompany(@PathVariable long membershipId,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        // When the user wants to exit the company or remove other user from company.
        final Membership membership = membershipService.findByMemberIdAndCompanyId(user.getId(), id).orElseThrow(NotFoundException::new);
        membershipService.findById(membershipId).orElseThrow(NotFoundException::new);

        if (membership.getId() == membershipId || membership.isConfirmed()) {
            membershipService.delete(membershipId);
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

}
