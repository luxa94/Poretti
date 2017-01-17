package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.RealEstateDTO;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.Owner;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.RealEstateType;
import com.bdzjn.poretti.model.enumeration.Role;
import com.bdzjn.poretti.util.data.CompanyTestData;
import com.bdzjn.poretti.util.data.RealEstateTestData;
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
public class RealEstateServiceTest {

    @Autowired
    private RealEstateService realEstateService;

    @Test
    @Transactional
    public void createShouldReturnSavedRealEstate() {
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        final Owner ownerTestEntity = ownerTestEntity();

        final RealEstate createdTestEntity = realEstateService.create(testEntity, ownerTestEntity);
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getType()).isEqualTo(testEntity.getType());
        assertThat(createdTestEntity.getAdvertisements()).hasSize(0);
        assertThat(createdTestEntity.getArea()).isEqualTo(testEntity.getArea());
        assertThat(createdTestEntity.getDescription()).isEqualTo(testEntity.getDescription());
        assertThat(createdTestEntity.getImageUrl()).isEqualTo(testEntity.getImageUrl());
        assertThat(createdTestEntity.getOwner().getId()).isEqualTo(ownerTestEntity.getId());
        assertThat(createdTestEntity.getName()).isEqualTo(testEntity.getName());
        assertThat(createdTestEntity.getTechnicalEquipment()).hasSize(1);
    }

    @Test
    @Transactional
    public void findByIdShouldReturnRealEstate() {
        final long existingId = 1L;

        final Optional<RealEstate> testEntity = realEstateService.findById(existingId);
        assertTrue(testEntity.isPresent());

        final RealEstate realEstate = testEntity.orElse(null);

        assertThat(realEstate.getId()).isEqualTo(existingId);
        assertThat(realEstate.getName()).isEqualTo("Test name");
        assertThat(realEstate.getOwner().getId()).isEqualTo(2);
        assertThat(realEstate.getLocation().getId()).isEqualTo(1);
        assertThat(realEstate.getArea()).isEqualTo(CompanyTestData.EDITED_AREA);
        assertThat(realEstate.getTechnicalEquipment()).hasSize(0);
        assertThat(realEstate.getAdvertisements()).hasSize(1);
        assertThat(realEstate.getDescription()).isEqualTo("Test description");
        assertThat(realEstate.getImageUrl()).isEqualTo("/images/defaultRealEstate.jpg");
        assertThat(realEstate.getType()).isEqualTo(RealEstateType.APARTMENT);
    }

    @Test
    @Transactional
    public void findByIdAndOwnerShouldReturnRealEstate() {
        final long existingId = 1L;
        final long ownerId = 2L;

        final Optional<RealEstate> testEntity = realEstateService.findByIdAndOwnerId(existingId, ownerId);
        assertTrue(testEntity.isPresent());

        final RealEstate realEstate = testEntity.orElse(null);

        assertThat(realEstate.getId()).isEqualTo(existingId);
        assertThat(realEstate.getName()).isEqualTo("Test name");
        assertThat(realEstate.getOwner().getId()).isEqualTo(2);
        assertThat(realEstate.getLocation().getId()).isEqualTo(1);
        assertThat(realEstate.getArea()).isEqualTo(CompanyTestData.EDITED_AREA);
        assertThat(realEstate.getTechnicalEquipment()).hasSize(0);
        assertThat(realEstate.getAdvertisements()).hasSize(1);
        assertThat(realEstate.getDescription()).isEqualTo("Test description");
        assertThat(realEstate.getImageUrl()).isEqualTo("/images/defaultRealEstate.jpg");
        assertThat(realEstate.getType()).isEqualTo(RealEstateType.APARTMENT);
    }

    @Test
    @Transactional
    public void editShouldReturnUpdatedRealEstate() {
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        testEntity.setId(1L);
        testEntity.setName("New test name");
        final long ownerId = 2L;

        final RealEstate editedTestEntity = realEstateService.edit(testEntity, ownerId);
        assertThat(editedTestEntity).isNotNull();

        assertThat(editedTestEntity.getType()).isEqualTo(testEntity.getType());
        assertThat(editedTestEntity.getAdvertisements()).hasSize(1);
        assertThat(editedTestEntity.getArea()).isEqualTo(testEntity.getArea());
        assertThat(editedTestEntity.getDescription()).isEqualTo(testEntity.getDescription());
        assertThat(editedTestEntity.getImageUrl()).isEqualTo(testEntity.getImageUrl());
        assertThat(editedTestEntity.getOwner().getId()).isEqualTo(ownerId);
        assertThat(editedTestEntity.getName()).isEqualTo(testEntity.getName());
        assertThat(editedTestEntity.getTechnicalEquipment()).hasSize(1);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void editShouldThrowExceptionWhenNonExistingRealEstate() {
        final long nonExistingId = 2L;
        final long advertiserId = 2L;
        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        testEntity.setId(nonExistingId);
        testEntity.setName("New real estate test name");

        realEstateService.edit(testEntity, advertiserId);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void editShouldThrowExceptionWhenCurrentUserIsNotOwner() {
        final long existingId = 1L;
        final long notAdvertiserId = 3L;

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        testEntity.setId(existingId);
        testEntity.setName("New real estate test name");

        realEstateService.edit(testEntity, notAdvertiserId);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void editShouldThrowExceptionWhenNonExistingRealEstateAndCurrentIsUserIsNotOwner() {
        final long nonExistingId = 2L;
        final long notAdvertiserId = 3L;

        final RealEstateDTO testEntity = RealEstateTestData.testEntity();
        testEntity.setId(nonExistingId);
        testEntity.setName("New real estate test name");

        realEstateService.edit(testEntity, notAdvertiserId);
    }

    @Test
    @Transactional
    public void delete() {
        final long dbSizeBeforeDeleting = 1;
        final long existingId = 1L;

        realEstateService.delete(existingId);
        //add assert for dbSize after findAll function.
    }

    private Owner ownerTestEntity() {
        User owner = new User();
        owner.setId(2);
        owner.setPassword("user");
        owner.setUsername("user");
        owner.setImageUrl("/imageUser.jpg");
        owner.setEmail("user@user.com");
        owner.setName("User");
        owner.setRegistrationConfirmed(true);
        owner.setRole(Role.USER);

        return owner;
    }
}
