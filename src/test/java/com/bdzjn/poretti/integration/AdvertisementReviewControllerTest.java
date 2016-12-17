package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.repository.AdvertisementReviewRepository;
import com.bdzjn.poretti.util.data.ReviewTestData;
import com.bdzjn.poretti.util.data.UserTestData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class AdvertisementReviewControllerTest {

    private static final String BASE_URL = "/api/advertisementReviews";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdvertisementReviewRepository reviewRepository;

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenReviewExistsAndCurrentUserIsAuthor() throws Exception {
        final String DELETE_REVIEW = BASE_URL + ReviewTestData.EXISTING_AD_REVIEW_ID_PATH;
        final int numberOfElementsBefore = reviewRepository.findAll().size();

        this.mockMvc.perform(delete(DELETE_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());

        final int numberOfElementsAfter = reviewRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore - 1));
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingReview() throws Exception {
        final String DELETE_REVIEW = BASE_URL + ReviewTestData.NON_EXISTING_AD_REVIEW_ID_PATH;

        this.mockMvc.perform(delete(DELETE_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAuthor() throws Exception {
        final String DELETE_REVIEW = BASE_URL + ReviewTestData.EXISTING_AD_REVIEW_ID_PATH;

        this.mockMvc.perform(delete(DELETE_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingReviewAndCurrentUserIsNotAuthor() throws Exception {
        final String DELETE_REVIEW = BASE_URL + ReviewTestData.NON_EXISTING_AD_REVIEW_ID_PATH;

        this.mockMvc.perform(delete(DELETE_REVIEW)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}