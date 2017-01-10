package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.service.AdvertisementService;
import com.bdzjn.poretti.service.OwnerReviewService;
import com.bdzjn.poretti.service.UserService;
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

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final OwnerReviewService ownerReviewService;
    private final AdvertisementService advertisementService;

    @Autowired
    public UserController(UserService userService, OwnerReviewService ownerReviewService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.ownerReviewService = ownerReviewService;
        this.advertisementService = advertisementService;
    }

    @PostMapping("/createSysAdmin")
    @PreAuthorize("hasAuthority('CREATE_SYSTEM_ADMIN')")
    public ResponseEntity crateAdmin(@RequestBody RegisterDTO registerDTO) {
        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())) {
            return new ResponseEntity<>("Username or email taken.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final User user = userService.createAdmin(registerDTO);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/createVerifier")
    @PreAuthorize("hasAuthority('CREATE_VERIFIER')")
    public ResponseEntity crateVerifier(@RequestBody RegisterDTO registerDTO) {
        if (userService.areUsernameOrEmailTaken(registerDTO.getUsername(), registerDTO.getEmail())) {
            return new ResponseEntity<>("Username or email taken.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final User user = userService.createVerifier(registerDTO);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody UserDTO userDTO,
                               @PathVariable long id,
                               @AuthenticationPrincipal User user) {
        if (user.getId() != id) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        userService.update(userDTO, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/advertisements")
    public ResponseEntity findAdvertisements(@PathVariable long id,
                                             @AuthenticationPrincipal User requester,
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
        userService.findById(id).orElseThrow(NotFoundException::new);
        final AdvertisementSearchCriteria searchCriteria = new AdvertisementSearchCriteria(realEstateName, areaFrom, areaTo, city,
                cityArea, state, street, latitude, longitude, realEstateType, advertisementTitle, advertisementType, advertisementStatus, priceFrom, priceTo, currency);
        if (requester == null || requester.getId() != id) {
            searchCriteria.setAdvertisementStatus(AdvertisementStatus.ACTIVE);
            final Page<Advertisement> advertisements = advertisementService.findActiveFor(id, searchCriteria, pageable);
            return new ResponseEntity<>(advertisements, HttpStatus.OK);
        }

        final Page<Advertisement> advertisements = advertisementService.findFor(id, searchCriteria, pageable);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/realEstates")
    public ResponseEntity findRealEstates(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);
        final List<RealEstate> realEstates = user.getRealEstates();
        return new ResponseEntity<>(realEstates, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/memberships")
    public ResponseEntity findMemberships(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);
        final List<Membership> memberships = user.getMemberships();

        return new ResponseEntity<>(memberships, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_REVIEW')")
    @Transactional
    @PostMapping("/{id}/reviews")
    public ResponseEntity createReview(@RequestBody ReviewDTO reviewDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        final User target = userService.findById(id).orElseThrow(NotFoundException::new);
        if (id == user.getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        final OwnerReview ownerReview = ownerReviewService.create(reviewDTO, target, user);
        return new ResponseEntity<>(ownerReview.getId(), HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/reviews")
    public ResponseEntity findReviews(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);
        final List<OwnerReview> reviews = user.getReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
