package com.bdzjn.poretti.service;

import com.bdzjn.poretti.controller.dto.ReviewDTO;
import com.bdzjn.poretti.controller.exception.ForbiddenException;
import com.bdzjn.poretti.controller.exception.NotFoundException;
import com.bdzjn.poretti.model.AdvertisementReview;
import com.bdzjn.poretti.model.User;
import com.bdzjn.poretti.model.enumeration.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class AdvertisementReviewServiceTest {

    @Autowired
    AdvertisementReviewService advertisementReviewService;

    @Test
    @Transactional
    public void createShouldReturnCreatedReview() {
        final long advertisementId = 1L;
        ReviewDTO testEntity = reviewTestEntity();
        User authorTestEntity = authorTestEntity();

        AdvertisementReview createdTestEntity = advertisementReviewService.create(testEntity, advertisementId, authorTestEntity);
        assertThat(createdTestEntity).isNotNull();

        assertThat(createdTestEntity.getComment()).isEqualTo(testEntity.getComment());
        assertThat(createdTestEntity.getRating()).isEqualTo(testEntity.getRating());
        assertThat(createdTestEntity.getTarget().getId()).isEqualTo(advertisementId);
        assertThat(createdTestEntity.getAuthor().getId()).isEqualTo(authorTestEntity.getId());
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void createShouldThrowExceptionWhenNonExistingAdvertisement() {
        final long nonExistingAdvertisementId = 2L;
        ReviewDTO testEntity = reviewTestEntity();
        User authorTestEntity = authorTestEntity();

        AdvertisementReview createdTestEntity = advertisementReviewService.create(testEntity, nonExistingAdvertisementId, authorTestEntity);
    }

    @Test(expected = ForbiddenException.class)
    @Transactional
    public void createShouldThrowExceptionWhenCurrentUserIsAdvertiser() {
        final long advertisementId = 1L;
        ReviewDTO testEntity = reviewTestEntity();
        User authorTestEntity = authorTestEntity();
        authorTestEntity.setId(2L);

        AdvertisementReview createdTestEntity = advertisementReviewService.create(testEntity, advertisementId, authorTestEntity);
    }

    @Test
    @Transactional
    public void delete() {
        final long existingId = 1L;
        final User authorTestEntity = authorTestEntity();

        advertisementReviewService.delete(existingId, authorTestEntity);
        //assert when findAll is implemented;
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void deleteShouldThrowExceptionWhenNonExistingReview() {
        final long nonExistingId = 2L;
        final User authorTestEntity = authorTestEntity();

        advertisementReviewService.delete(nonExistingId, authorTestEntity);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void deleteShouldThrowExceptionWhenCurrentUserIsNotAuthor() {
        final long existingId = 1L;
        final User authorTestEntity = authorTestEntity();
        authorTestEntity.setId(2L);

        advertisementReviewService.delete(existingId, authorTestEntity);
    }

    @Test(expected = NotFoundException.class)
    @Transactional
    public void deleteShouldThrowExceptionWhenCurrentUserIsNotAuthorAndNonExistingReview() {
        final long nonExistingId = 2L;
        final User authorTestEntity = authorTestEntity();
        authorTestEntity.setId(2L);

        advertisementReviewService.delete(nonExistingId, authorTestEntity);
    }

    private ReviewDTO reviewTestEntity() {
        final ReviewDTO review = new ReviewDTO();
        review.setComment("Test review comment");
        review.setRating(5);

        return review;
    }

    private User authorTestEntity() {
        User user = new User();
        user.setId(3);
        user.setPassword("user");
        user.setUsername("user");
        user.setImageUrl("/imageUser.jpg");
        user.setEmail("user@user.com");
        user.setName("User");
        user.setRegistrationConfirmed(true);
        user.setRole(Role.USER);

        return user;
    }
}