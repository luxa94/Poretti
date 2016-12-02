package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.*;
import com.bdzjn.poretti.service.AdvertisementReviewService;
import com.bdzjn.poretti.service.AdvertisementService;
import com.bdzjn.poretti.service.ImproperAdvertisementReportService;
import com.bdzjn.poretti.service.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final RealEstateService realEstateService;
    private final AdvertisementService advertisementService;
    private final AdvertisementReviewService advertisementReviewService;
    private final ImproperAdvertisementReportService improperAdvertisementReportService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService, RealEstateService realEstateService, AdvertisementReviewService advertisementReviewService, ImproperAdvertisementReportService improperAdvertisementReportService) {
        this.realEstateService = realEstateService;
        this.advertisementService = advertisementService;
        this.advertisementReviewService = advertisementReviewService;
        this.improperAdvertisementReportService = improperAdvertisementReportService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity find(Pageable pageable) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        final Advertisement advertisement = advertisementService.findById(id).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody AdvertisementRealEstateDTO advertisementRealEstateDTO,
                                 @AuthenticationPrincipal User user) {
        final AdvertisementDTO advertisementDTO = advertisementRealEstateDTO.getAdvertisementDTO();
        final RealEstateDTO realEstateDTO = advertisementRealEstateDTO.getRealEstateDTO();

        final RealEstate realEstate = realEstateService.create(realEstateDTO, user);
        final Advertisement advertisement = advertisementService.create(advertisementDTO, realEstate);
        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable long id,
                               @RequestBody AdvertisementDTO advertisementDTO,
                               @AuthenticationPrincipal User user) {
        advertisementDTO.setId(id);
        final Advertisement advertisement = advertisementService.edit(advertisementDTO, user.getId());

        return new ResponseEntity<>(advertisement, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User user, @PathVariable long id) {
        advertisementService.findByIdAndOwnerId(id, user.getId()).orElseThrow(NotFoundException::new);
        advertisementService.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT_REPORT')")
    @Transactional
    @PostMapping("/{id}/reports")
    public ResponseEntity createReport(@RequestBody AdvertisementReportDTO advertisementReportDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        final ImproperAdvertisementReport report = improperAdvertisementReportService.create(advertisementReportDTO, id, user);
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/reports")
    public ResponseEntity findReports(@PathVariable long id) {
        final Advertisement advertisement = advertisementService.findById(id).orElseThrow(NotFoundException::new);
        final List<ImproperAdvertisementReport> reports = advertisement.getReports();

        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_REVIEW')")
    @Transactional
    @PostMapping("/{id}/reviews")
    public ResponseEntity createReview(@RequestBody ReviewDTO reviewDTO,
                                       @PathVariable long id,
                                       @AuthenticationPrincipal User user) {
        final AdvertisementReview review = advertisementReviewService.create(reviewDTO, id, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @Transactional
    @GetMapping("/{id}/reviews")
    public ResponseEntity findReviews(@PathVariable long id) {
        final Advertisement advertisement = advertisementService.findById(id).orElseThrow(NotFoundException::new);
        final List<AdvertisementReview> reviews = advertisement.getReviews();

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
