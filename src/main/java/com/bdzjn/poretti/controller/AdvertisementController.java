package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.AdvertisementService;
import com.bdzjn.poretti.service.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;
    private final RealEstateService realEstateService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService, RealEstateService realEstateService) {
        this.advertisementService = advertisementService;
        this.realEstateService = realEstateService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity find(Pageable pageable) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        Advertisement advertisement = advertisementService.findById(id).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody AdvertisementRealEstateDTO advertisementRealEstateDTO,
                                 @AuthenticationPrincipal User user) {
        AdvertisementDTO advertisementDTO = advertisementRealEstateDTO.getAdvertisementDTO();
        RealEstateDTO realEstateDTO = advertisementRealEstateDTO.getRealEstateDTO();

        advertisementDTO.setId(0);
        realEstateDTO.setId(0);
        RealEstate realEstate = realEstateService.save(realEstateDTO, user);
        Advertisement advertisement = advertisementService.save(advertisementDTO, realEstate);
        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable long id,
                               @RequestBody AdvertisementDTO advertisementDTO,
                               @AuthenticationPrincipal User user) {
        advertisementDTO.setId(id);

        Advertisement advertisement = advertisementService.update(advertisementDTO, user.getId());

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User user, @PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT_REPORT')")
    @Transactional
    @PostMapping("/{id}/reports")
    public ResponseEntity createReport(@RequestBody AdvertisementReportDTO advertisementReportDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal long userId) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}/reports")
    public ResponseEntity findReports(@PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_REVIEW')")
    @Transactional
    @PostMapping("/{id}/reviews")
    public ResponseEntity createReview(@RequestBody ReviewDTO reviewDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal long userId) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}/reviews")
    public ResponseEntity findReviews() {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

}
