package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.*;
import com.bdzjn.poretti.model.enumeration.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class AdvertisementServiceTest {

    @Autowired
    AdvertisementService advertisementService;

    @Test
    @Transactional
    public void createShouldReturnSavedAdvertisement(){
        final AdvertisementDTO testEntity = advertisementTestEntity();
        final RealEstate realEstateTestEntity = realEstateTestEntity();

        Advertisement createdTestEntity = advertisementService.create(testEntity, realEstateTestEntity);
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getPrice()).isEqualTo(testEntity.getPrice());
        assertThat(createdTestEntity.getReports()).hasSize(0);
        assertThat(createdTestEntity.getReviews()).hasSize(0);
        assertThat(createdTestEntity.getRealEstate().getId()).isEqualTo(realEstateTestEntity.getId());
        assertThat(createdTestEntity.getAdvertiser().getId()).isEqualTo(2L);
        assertThat(createdTestEntity.getCurrency()).isEqualTo(testEntity.getCurrency());
        assertThat(createdTestEntity.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(createdTestEntity.getStatus()).isEqualTo(AdvertisementStatus.ACTIVE);
        assertThat(createdTestEntity.getType()).isEqualTo(testEntity.getType());
    }

    @Test
    @Transactional
    public void findByIdShouldReturnAdvertisement() {
        final long existingId = 1L;

        Optional<Advertisement> testEntity = advertisementService.findById(existingId);
        assertTrue(testEntity.isPresent());

        Advertisement advertisement = testEntity.get();

        assertThat(advertisement.getId()).isEqualTo(existingId);
        assertThat(advertisement.getTitle()).isEqualTo("Advertisement title");
        assertThat(advertisement.getAdvertiser().getId()).isEqualTo(2);
        assertThat(advertisement.getCurrency()).isEqualTo(Currency.RSD);
        assertThat(advertisement.getPrice()).isEqualTo(new Double(3000));
        assertThat(advertisement.getRealEstate().getId()).isEqualTo(1);
        assertThat(advertisement.getReports()).hasSize(0);
        assertThat(advertisement.getReviews()).hasSize(1);
    }

    @Test
    @Transactional
    public void findByIdAndOwnerShouldReturnAdvertisement() {
        final long existingId = 1L;
        final long advertiserId = 2L;

        Optional<Advertisement> testEntity = advertisementService.findByIdAndOwnerId(existingId, advertiserId);

        Advertisement advertisement = testEntity.get();

        assertThat(advertisement.getTitle()).isEqualTo("Advertisement title");
        assertThat(advertisement.getAdvertiser().getId()).isEqualTo(advertiserId);
        assertThat(advertisement.getCurrency()).isEqualTo(Currency.RSD);
        assertThat(advertisement.getPrice()).isEqualTo(new Double(3000));
        assertThat(advertisement.getRealEstate().getId()).isEqualTo(1);
        assertThat(advertisement.getReports()).hasSize(0);
        assertThat(advertisement.getReviews()).hasSize(1);

    }

    @Test
    @Transactional
    public void editShouldReturnUpdatedAdvertisement() {
        final AdvertisementDTO testEntity = advertisementTestEntity();
        testEntity.setId(1L);
        testEntity.setTitle("New advertisement test title");
        final long advertiserId = 2L;
        final long realEstateId = 1L;

        Advertisement editedTestEntity = advertisementService.edit(testEntity, advertiserId);
        assertThat(editedTestEntity).isNotNull();

        assertThat(editedTestEntity.getTitle()).isEqualTo(testEntity.getTitle());
        assertThat(editedTestEntity.getStatus()).isEqualTo(AdvertisementStatus.ACTIVE);
        assertThat(editedTestEntity.getCurrency()).isEqualTo(testEntity.getCurrency());
        assertThat(editedTestEntity.getType()).isEqualTo(testEntity.getType());
        assertThat(editedTestEntity.getPrice()).isEqualTo(testEntity.getPrice());
        assertThat(editedTestEntity.getAdvertiser().getId()).isEqualTo(advertiserId);
        assertThat(editedTestEntity.getRealEstate().getId()).isEqualTo(realEstateId);
        assertThat(editedTestEntity.getReports()).hasSize(0);
        assertThat(editedTestEntity.getReviews()).hasSize(1);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void editShouldThrowExceptionWhenNonExistingAdvertisement() {
        final long nonExistingId = 2L;
        final long advertiserId = 2L;
        AdvertisementDTO testEntity = advertisementTestEntity();
        testEntity.setId(nonExistingId);
        testEntity.setTitle("New advertisement test title");

        advertisementService.edit(testEntity, advertiserId);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void editShouldThrowExceptionWhenCurrentUserIsNotAdvertiser() {
        final long existingId = 1L;
        final long notAdvertiserId = 3L;

        AdvertisementDTO testEntity = advertisementTestEntity();
        testEntity.setId(existingId);
        testEntity.setTitle("New advertisement test title");

        advertisementService.edit(testEntity, notAdvertiserId);
    }

    @Test
    @Transactional
    public void delete() {
        final long dbSizeBeforeDeleting = 1;
        final long existingId = 1L;

        advertisementService.delete(existingId);
        //add assert for dbSize after findAll function.
    }

    private AdvertisementDTO advertisementTestEntity() {
        AdvertisementDTO advertisement = new AdvertisementDTO();
        advertisement.setPrice(100000);
        advertisement.setCurrency(Currency.RSD);
        advertisement.setTitle("Test Advertisement");
        advertisement.setType(AdvertisementType.SALE);
        advertisement.setEndsOn(new Date());
        return advertisement;
    }

    private RealEstate realEstateTestEntity() {
        Location location = new Location();
        location.setCity("Location test city");
        location.setLatitude(1);
        location.setLongitude(1);
        location.setHasLatLong(true);

        User owner = new User();
        owner.setId(2);
        owner.setPassword("user");
        owner.setUsername("user");
        owner.setImageUrl("/imageUser.jpg");
        owner.setEmail("user@user.com");
        owner.setName("User");
        owner.setRegistrationConfirmed(true);
        owner.setRole(Role.USER);

        RealEstate realEstate = new RealEstate();
        realEstate.setId(1);
        realEstate.setDescription("Real estate test description");
        realEstate.setImageUrl("/images/defaultRealEstate.jpg");
        realEstate.setType(RealEstateType.APARTMENT);
        realEstate.setName("Real estate name");
        realEstate.setLocation(location);
        realEstate.setOwner(owner);
        realEstate.setArea(new Double(100));

        return realEstate;
    }
}
