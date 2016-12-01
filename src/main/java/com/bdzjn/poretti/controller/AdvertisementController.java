package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.AdvertisementRealEstateDTO;
import com.bdzjn.poretti.controller.dto.AdvertisementReportDTO;
import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.AdvertisementService;
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

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity find(Pageable pageable) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody AdvertisementRealEstateDTO advertisementRealEstateDTO, @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@AuthenticationPrincipal User user, @PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal User user, @PathVariable long id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAnyAuthority('REPORT_ADVERTISEMENT')")
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
