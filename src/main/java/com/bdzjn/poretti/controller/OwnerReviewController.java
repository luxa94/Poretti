package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.service.OwnerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ownerReviews")
public class OwnerReviewController {

    private OwnerReviewService ownerReviewService;

    @Autowired
    public OwnerReviewController(OwnerReviewService ownnerReviewService){
        this.ownerReviewService = ownerReviewService;
    }
    

}
