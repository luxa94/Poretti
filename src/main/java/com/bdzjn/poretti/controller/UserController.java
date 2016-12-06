package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.RegisterDTO;
import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.dto.UserDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.*;
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
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        userService.update(userDTO, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/advertisements")
    public ResponseEntity findAdvertisements(@PathVariable long id,
                                             @AuthenticationPrincipal User requester,
                                             Pageable pageable) {
        if (requester == null || requester.getId() != id) {
            final Page<Advertisement> advertisements = advertisementService.findActiveByUser(id, pageable);
            return new ResponseEntity<>(advertisements, HttpStatus.OK);
        }

        // TODO: Paging, sorting, filtering.
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);
        final List<Advertisement> advertisements = user.getAdvertisements();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/realEstates")
    public ResponseEntity findRealEstates(@PathVariable long id) {
        // TODO: Paging, sorting, filtering.
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
        final OwnerReview ownerReview = ownerReviewService.create(reviewDTO, target, user);
        return new ResponseEntity<>(ownerReview, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/reviews")
    public ResponseEntity findReviews(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);
        final List<OwnerReview> reviews = user.getReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
