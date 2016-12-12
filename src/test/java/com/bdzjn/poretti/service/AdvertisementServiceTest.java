package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.AdvertisementDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.Location;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.model.enumeration.Role;
import com.bdzjn.poretti.util.data.AdvertisementTestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class AdvertisementServiceTest {

    @Autowired
    private AdvertisementService advertisementService;

    @Test
    @Transactional
    public void createShouldReturnSavedAdvertisement() {
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        final RealEstate realEstateTestEntity = realEstateTestEntity();

        final Advertisement createdTestEntity = advertisementService.create(testEntity, realEstateTestEntity);
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

        final Optional<Advertisement> testEntity = advertisementService.findById(existingId);
        assertTrue(testEntity.isPresent());

        final Advertisement advertisement = testEntity.get();

        assertThat(advertisement.getId()).isEqualTo(existingId);
        assertThat(advertisement.getTitle()).isEqualTo("Advertisement title");
        assertThat(advertisement.getAdvertiser().getId()).isEqualTo(2);
        assertThat(advertisement.getPrice()).isEqualTo(AdvertisementTestData.EXISTING_PRICE);
        assertThat(advertisement.getRealEstate().getId()).isEqualTo(1);
        assertThat(advertisement.getReports()).hasSize(0);
        assertThat(advertisement.getReviews()).hasSize(1);
    }

    @Test
    @Transactional
    public void findByIdAndOwnerShouldReturnAdvertisement() {
        final long existingId = 1L;
        final long advertiserId = 2L;

        final Optional<Advertisement> testEntity = advertisementService.findByIdAndOwnerId(existingId, advertiserId);
        assertTrue(testEntity.isPresent());
        final Advertisement advertisement = testEntity.get();

        assertThat(advertisement.getTitle()).isEqualTo("Advertisement title");
        assertThat(advertisement.getAdvertiser().getId()).isEqualTo(advertiserId);
        assertThat(advertisement.getPrice()).isEqualTo(AdvertisementTestData.EXISTING_PRICE);
        assertThat(advertisement.getRealEstate().getId()).isEqualTo(1);
        assertThat(advertisement.getReports()).hasSize(0);
        assertThat(advertisement.getReviews()).hasSize(1);

    }

    @Test
    @Transactional
    public void editShouldReturnUpdatedAdvertisement() {
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        testEntity.setId(1L);
        testEntity.setTitle("New advertisement test title");
        final long advertiserId = 2L;
        final long realEstateId = 1L;

        final Advertisement editedTestEntity = advertisementService.edit(testEntity, advertiserId);
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
        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
        testEntity.setId(nonExistingId);
        testEntity.setTitle("New advertisement test title");

        advertisementService.edit(testEntity, advertiserId);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void editShouldThrowExceptionWhenCurrentUserIsNotAdvertiser() {
        final long existingId = 1L;
        final long notAdvertiserId = 3L;

        final AdvertisementDTO testEntity = AdvertisementTestData.testEntity();
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

    private RealEstate realEstateTestEntity() {
        final Location location = new Location();
        location.setCity("Location test city");
        location.setLatitude(1);
        location.setLongitude(1);
        location.setHasLatLong(true);

        final User owner = new User();
        owner.setId(2);
        owner.setPassword("user");
        owner.setUsername("user");
        owner.setImageUrl("/imageUser.jpg");
        owner.setEmail("user@user.com");
        owner.setName("User");
        owner.setRegistrationConfirmed(true);
        owner.setRole(Role.USER);

        final RealEstate realEstate = new RealEstate();
        realEstate.setId(1);
        realEstate.setDescription("Real estate test description");
        realEstate.setImageUrl("/images/defaultRealEstate.jpg");
        realEstate.setType(RealEstateType.APARTMENT);
        realEstate.setName("Real estate name");
        realEstate.setLocation(location);
        realEstate.setOwner(owner);
        realEstate.setArea(100d);

        return realEstate;
    }
}
