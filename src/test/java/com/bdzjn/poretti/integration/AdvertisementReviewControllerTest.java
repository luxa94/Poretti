package com.bdzjn.poretti.integration;

import com.bdzjn.poretti.repository.AdvertisementReviewRepository;
import com.bdzjn.poretti.util.data.ReviewTestData;
import com.bdzjn.poretti.util.data.UserTestData;
import com.bdzjn.poretti.util.snippets.AuthorizationSnippets;
import com.bdzjn.poretti.util.snippets.ReviewSnippets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ActiveProfiles(profiles = "test")
public class AdvertisementReviewControllerTest {

    private static final String BASE_URL = "/api/advertisementReviews";
    private final String DELETE_REVIEW = BASE_URL + ReviewTestData.ID_PATH_VARIABLE;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdvertisementReviewRepository reviewRepository;

    @Test
    @Transactional
    public void deleteShouldReturnOkWhenReviewExistsAndCurrentUserIsAuthor() throws Exception {
        final int numberOfElementsBefore = reviewRepository.findAll().size();

        this.mockMvc.perform(delete(DELETE_REVIEW, ReviewTestData.EXISTING_AD_REVIEW_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("delete-advertisement-review",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        AuthorizationSnippets.AUTH_HEADER,
                        pathParameters(ReviewSnippets.AD_REVIEW_ID)));

        final int numberOfElementsAfter = reviewRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore - 1));
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingReview() throws Exception {
        final int numberOfElementsBefore = reviewRepository.findAll().size();

        this.mockMvc.perform(delete(DELETE_REVIEW, ReviewTestData.NON_EXISTING_AD_REVIEW_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.NOT_OWNER_TOKEN))
                .andDo(print())
                .andExpect(status().isNotFound());

        final int numberOfElementsAfter = reviewRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenCurrentUserIsNotAuthor() throws Exception {
        final int numberOfElementsBefore = reviewRepository.findAll().size();

        this.mockMvc.perform(delete(DELETE_REVIEW, ReviewTestData.EXISTING_AD_REVIEW_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());

        final int numberOfElementsAfter = reviewRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

    @Test
    @Transactional
    public void deleteShouldReturnNotFoundWhenNonExistingReviewAndCurrentUserIsNotAuthor() throws Exception {
        final int numberOfElementsBefore = reviewRepository.findAll().size();

        this.mockMvc.perform(delete(DELETE_REVIEW, ReviewTestData.NON_EXISTING_AD_REVIEW_ID)
                .header(UserTestData.AUTHORIZATION, UserTestData.TOKEN_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());

        final int numberOfElementsAfter = reviewRepository.findAll().size();
        Assert.assertThat(numberOfElementsAfter, is(numberOfElementsBefore));
    }

}
