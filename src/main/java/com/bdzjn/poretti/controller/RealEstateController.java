package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.service.RealEstateService;
import com.mysql.fabric.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/realEstates")
public class RealEstateController {

    private RealEstateService realEstateService;

    @Autowired
    public RealEstateController(RealEstateService realEstateService){
        this.realEstateService = realEstateService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity find(Pageable pageable){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping
    public ResponseEntity create(@AuthenticationPrincipal User user){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable long id, @AuthenticationPrincipal User user){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id, @AuthenticationPrincipal User user){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @Transactional
    @GetMapping({"/{id}/advertisements"})
    public ResponseEntity findAdvertisements(@PathVariable long id, Pageable pageable){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/advertisements")
    public ResponseEntity createAdvertisement(@AuthenticationPrincipal User user){
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }



}
