package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.OwnerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ownerReviews")
public class OwnerReviewController {

    private OwnerReviewService ownerReviewService;

    @Autowired
    public OwnerReviewController(OwnerReviewService ownerReviewService){
        this.ownerReviewService = ownerReviewService;
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id, @AuthenticationPrincipal User user){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
