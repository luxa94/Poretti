package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.controller.dto.*;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.*;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.AdvertisementType;
import com.bdzjn.poretti.model.enumeration.Currency;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.service.AdvertisementReviewService;
import com.bdzjn.poretti.service.AdvertisementService;
import com.bdzjn.poretti.service.ImproperAdvertisementReportService;
import com.bdzjn.poretti.service.RealEstateService;
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
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final RealEstateService realEstateService;
    private final AdvertisementService advertisementService;
    private final AdvertisementReviewService advertisementReviewService;
    private final ImproperAdvertisementReportService improperAdvertisementReportService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService, RealEstateService realEstateService,
                                   AdvertisementReviewService advertisementReviewService, ImproperAdvertisementReportService improperAdvertisementReportService) {
        this.realEstateService = realEstateService;
        this.advertisementService = advertisementService;
        this.advertisementReviewService = advertisementReviewService;
        this.improperAdvertisementReportService = improperAdvertisementReportService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity find(@RequestParam(required = false) String realEstateName,
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
                               @RequestParam(required = false) Double priceFrom,
                               @RequestParam(required = false) Double priceTo,
                               @RequestParam(required = false) Currency currency,
                               Pageable pageable) {
        final AdvertisementSearchCriteria searchCriteria = new AdvertisementSearchCriteria(realEstateName, areaFrom, areaTo, city,
                cityArea, state, street, latitude, longitude, realEstateType, advertisementTitle, advertisementType, AdvertisementStatus.ACTIVE, priceFrom, priceTo, currency);
        final Page<Advertisement> advertisements = advertisementService.findActive(searchCriteria, pageable);
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
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
    public ResponseEntity delete(@PathVariable long id,
                                 @AuthenticationPrincipal User user) {
        advertisementService.findByIdAndOwnerId(id, user.getId()).orElseThrow(NotFoundException::new);
        advertisementService.delete(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CHANGE_ADVERTISEMENT_STATUS')")
    @Transactional
    @PutMapping("/{id}/invalidate")
    public ResponseEntity invalidate(@PathVariable long id) {
        advertisementService.changeStatus(id, AdvertisementStatus.INVALID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CHANGE_ADVERTISEMENT_STATUS')")
    @Transactional
    @PutMapping("/{id}/approve")
    public ResponseEntity approve(@PathVariable long id) {
        advertisementService.changeStatus(id, AdvertisementStatus.ACTIVE);
        advertisementService.deleteReportsAfterApprove(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}/done")
    public ResponseEntity done(@PathVariable long id, @AuthenticationPrincipal User user){
        advertisementService.findByIdAndOwnerId(id,user.getId()).orElseThrow(NotFoundException::new);
        advertisementService.changeStatus(id, AdvertisementStatus.DONE);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CHANGE_ADVERTISEMENT_STATUS')")
    @Transactional
    @GetMapping("/reported")
    public ResponseEntity findReported() {
        final List<Advertisement> reported = advertisementService.findReported();
        return new ResponseEntity<>(reported, HttpStatus.OK);
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
