package com.bdzjn.poretti.util.data;

import com.bdzjn.poretti.controller.dto.ReviewDTO;

public class ReviewTestData {

    public static final String EXISTING_COMMENT = "";
    public static final double EXISTING_RATING = 0;
    public static final String EXISTING_AD_REVIEW_ID_PATH = "/1";
    public static final String EXISTING_OWNER_REVIEW_ID_PATH = "/1";
    public static final String NON_EXISTING_AD_REVIEW_ID_PATH = "/2";
    public static final String NON_EXISTING_OWNER_REVIEW_ID_PATH = "/2";
    public static final int EXISTING_AD_REVIEW_ID = 1;
    public static final int EXISTING_OWNER_REVIEW_ID = 1;

    public static ReviewDTO testEntity() {
        final ReviewDTO review = new ReviewDTO();
        review.setComment("Test review comment");
        review.setRating(5);

        return review;
    }

}
