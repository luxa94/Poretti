package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.exception.BadDateException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    /**
     * Creates new advertisement and real estate also.
     * In case if date, when advertisement ends, is before today {@link BadDateException} is thrown.
     *
     * @param advertisementDTO
     * @param realEstate
     * @return
     */
    Advertisement create(AdvertisementDTO advertisementDTO, RealEstate realEstate);

    Optional<Advertisement> findById(long id);

    Optional<Advertisement> findByIdAndOwnerId(long id, long advertiserId);

    /**
     * Finds all advertisements which have reports.
     * @return
     */
    List<Advertisement> findReported();

    /**
     * Finds all advertisements from advertiser with the given id and criteria for filtering.
     *
     * @param advertiserId
     * @param searchCriteria
     * @param pageable
     * @return
     */
    Page<Advertisement> findFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    /**
     * Finds all advertisements from advertiser with the given id and criteria for filtering.
     * Only active advertisements are collected.
     *
     * @param advertiserId
     * @param searchCriteria
     * @param pageable
     * @return
     */
    Page<Advertisement> findActiveFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    /**
     * Finds all advertisements with the given criteria.
     *
     * @param searchCriteria
     * @param pageable
     * @return
     */
    Page<Advertisement> findActive(AdvertisementSearchCriteria searchCriteria, Pageable pageable);

    /**
     * Edits advertisement of given owner.
     * In case if advertisement doesn't exist and ownerId is not id of actual advertiser  {@link NotFoundException} is thrown.
     * In case if date, when advertisement ends, is before today {@link BadDateException} is thrown.
     * @param advertisementDTO
     * @param ownerId
     * @return
     */
    Advertisement edit(AdvertisementDTO advertisementDTO, long ownerId);

    void delete(long id);

    /**
     * Changes status for advertisement with the given id.
     * In case when advertisement doesn't exists {@link NotFoundException} is thrown.
     *
     * @param id
     * @param status
     */
    void changeStatus(long id, AdvertisementStatus status);

    /**
     * Deletes reports of advertisement with the given id.
     * In case when advertisement doesn't exists {@link NotFoundException} is thrown.
     *
     * @param id
     */
    void deleteReportsAfterApprove(long id);
}
