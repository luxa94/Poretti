package com.bdzjn.poretti.util.snippets;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class ReviewSnippets {

    public static final FieldDescriptor[] AD_REVIEW = new FieldDescriptor[]{
            fieldWithPath("comment").description("Comment of review for specified advertisment"),
            fieldWithPath("rating").description("Rating of advertisement. Must be between 1 and 10, inclusive")
    };

    public static final FieldDescriptor[] OWNER_REVIEW = new FieldDescriptor[]{
            fieldWithPath("comment").description("Comment of review for specified user"),
            fieldWithPath("rating").description("Rating of user. Must be between 1 and 10, inclusive")
    };

    public static final ParameterDescriptor[] AD_REVIEW_ID = new ParameterDescriptor[]{
            parameterWithName("id").description("Id of advertisement review to be deleted")
    };

    public static final ParameterDescriptor[] OWNER_REVIEW_ID = new ParameterDescriptor[]{
            parameterWithName("id").description("Id of owner review to be deleted")
    };

}
