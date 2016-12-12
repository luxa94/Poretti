package com.bdzjn.poretti.controller;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.dto.RealEstateDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/realEstates")
public class RealEstateController {

    private final RealEstateService realEstateService;
    private final AdvertisementService advertisementService;

    @Autowired
    public RealEstateController(RealEstateService realEstateService, AdvertisementService advertisementService) {
        this.realEstateService = realEstateService;
        this.advertisementService = advertisementService;
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        final RealEstate realEstate = realEstateService.findById(id).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody RealEstateDTO realEstateDTO,
                                 @AuthenticationPrincipal User user) {
        final RealEstate realEstate = realEstateService.create(realEstateDTO, user);
        return new ResponseEntity<>(realEstate, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_ADVERTISEMENT')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity edit(@PathVariable long id,
                               @RequestBody RealEstateDTO realEstateDTO,
                               @AuthenticationPrincipal User user) {
        realEstateDTO.setId(id);
        final RealEstate realEstate = realEstateService.edit(realEstateDTO, user.getId());

        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('DELETE_ADVERTISEMENT')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable long id,
                                 @AuthenticationPrincipal User user) {
        realEstateService.findByIdAndOwnerId(id, user.getId()).orElseThrow(NotFoundException::new);
        realEstateService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @GetMapping({"/{id}/advertisements"})
    public ResponseEntity findAdvertisements(@PathVariable long id) {
        final RealEstate realEstate = realEstateService.findById(id).orElseThrow(NotFoundException::new);
        final List<Advertisement> advertisements = realEstate.getAdvertisements();

        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CREATE_ADVERTISEMENT')")
    @Transactional
    @PostMapping("/{id}/advertisements")
    public ResponseEntity createAdvertisement(@PathVariable long id,
                                              @RequestBody AdvertisementDTO advertisementDTO,
                                              @AuthenticationPrincipal User user) {
        final RealEstate realEstate = realEstateService.findByIdAndOwnerId(id, user.getId()).orElseThrow(NotFoundException::new);
        final Advertisement advertisement = advertisementService.create(advertisementDTO, realEstate);

        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

}
