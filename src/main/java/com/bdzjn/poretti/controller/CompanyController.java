package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.controller.response.MessageResponse;
import com.bdzjn.poretti.model.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public CompanyController(CompanyService companyService, UserService userService,
                             MembershipService membershipService, RealEstateService realEstateService,
                             OwnerReviewService ownerReviewService, AdvertisementService advertisementService) {
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
    @GetMapping
    public ResponseEntity findAll(){
        final List<Company> companies = companyService.findAll();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_COMPANY')")
    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody RegisterCompanyDTO registerCompanyDTO,
                                 @AuthenticationPrincipal User admin) {
        final CompanyDTO companyDTO = registerCompanyDTO.getCompanyDTO();
        final RegisterDTO registerDTO = registerCompanyDTO.getRegisterDTO();

        if (companyDTO == null || registerDTO == null) {
            return new ResponseEntity<>(new MessageResponse("Must specify user and company."), HttpStatus.BAD_REQUEST);
        }

        if (companyService.findByPib(companyDTO.getPib()).isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Company with given pib already exists."), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("Username or email taken."), HttpStatus.UNPROCESSABLE_ENTITY);
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

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}/realEstates/{realEstateId}")
    public ResponseEntity deleteRealEstate(@PathVariable long id,
                                           @PathVariable long realEstateId,
                                           @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        realEstateService.findByIdAndOwnerId(realEstateId, id).orElseThrow(NotFoundException::new);
        realEstateService.delete(realEstateId);

        return new ResponseEntity(HttpStatus.OK);
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
    public ResponseEntity findAdvertisements(@PathVariable long id,
                                             @AuthenticationPrincipal User user,
                                             @RequestParam(required = false) String realEstateName,
                                             @RequestParam(required = false) Double areaFrom,
                                             @RequestParam(required = false) Double areaTo,
                                             @RequestParam(required = false) String city,
                                             @RequestParam(required = false) String cityArea,
                                             @RequestParam(required = false) String state,
                                             @RequestParam(required = false) String street,
                                             @RequestParam(required = false) Double latitude,
                                             @RequestParam(required = false) Double longitude,
                                             @RequestParam(required = false) RealEstateType realEstateType,
                                             @RequestParam(required = false) String advertisementTitle,
                                             @RequestParam(required = false) AdvertisementType advertisementType,
                                             @RequestParam(required = false) AdvertisementStatus advertisementStatus,
                                             @RequestParam(required = false) Double priceFrom,
                                             @RequestParam(required = false) Double priceTo,
                                             @RequestParam(required = false) Currency currency,
                                             Pageable pageable) {
        companyService.findById(id).orElseThrow(NotFoundException::new);
        final AdvertisementSearchCriteria searchCriteria = new AdvertisementSearchCriteria(realEstateName, areaFrom, areaTo, city,
                cityArea, state, street, latitude, longitude, realEstateType, advertisementTitle, advertisementType, advertisementStatus, priceFrom, priceTo, currency);
        if (user != null) {
            final Optional<Membership> membership = membershipService.findByMemberIdAndCompanyId(user.getId(), id);
            if (membership.isPresent() && membership.get().isConfirmed()) {
                final Page<Advertisement> advertisements = advertisementService.findFor(id, searchCriteria, pageable);
                return new ResponseEntity<>(advertisements, HttpStatus.OK);
            }
        }
        searchCriteria.setAdvertisementStatus(null);
        final Page<Advertisement> advertisements = advertisementService.findActiveFor(id, searchCriteria, pageable);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
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

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}/advertisements/{advertisementId}/done")
    public ResponseEntity done(@PathVariable long id,
                               @PathVariable long advertisementId,
                               @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        advertisementService.changeStatus(advertisementId, AdvertisementStatus.DONE);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}/advertisements/{advertisementId}")
    public ResponseEntity deleteAdvertisement(@PathVariable long id,
                                              @PathVariable long advertisementId,
                                              @AuthenticationPrincipal User user) {
        membershipService.findActiveMembership(user.getId(), id);
        advertisementService.findByIdAndOwnerId(advertisementId, id).orElseThrow(NotFoundException::new);
        advertisementService.delete(advertisementId);

        return new ResponseEntity(HttpStatus.OK);
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
    public ResponseEntity createMembership(@PathVariable long id,
                                           @AuthenticationPrincipal User user) {
        // Request for the user to join the company.
        final Company company = companyService.findById(id).orElseThrow(NotFoundException::new);
        membershipService.findByMemberIdAndCompanyId(user.getId(), id).ifPresent(membership -> {
            throw new ForbiddenException("Request already pending for approval or accepted.");
        });

        final Membership membership = membershipService.save(user, company);
        return new ResponseEntity<>(membership.getId(), HttpStatus.CREATED);
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
            return new ResponseEntity<>(new MessageResponse("Already accepted."), HttpStatus.CONFLICT);
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
