package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.AdvertisementReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/advertisementReviews")
public class AdvertisementReviewController {

    private final AdvertisementReviewService advertisementReviewService;

    @Autowired
    public AdvertisementReviewController(AdvertisementReviewService advertisementReviewService) {
        this.advertisementReviewService = advertisementReviewService;
    }

    @PreAuthorize("hasAnyAuthority('CREATE_REVIEW')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity edit(@PathVariable long id, @AuthenticationPrincipal User user) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
    
}
