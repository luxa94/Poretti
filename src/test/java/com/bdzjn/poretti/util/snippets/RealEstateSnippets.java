package com.bdzjn.poretti.util.snippets;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class RealEstateSnippets {

    public static final ParameterDescriptor[] REAL_ESTATE_ID = new ParameterDescriptor[]{
            parameterWithName("id").description("Id of real estate")
    };

    public static final FieldDescriptor[] REAL_ESTATE_DTO = new FieldDescriptor[] {
            fieldWithPath("id").ignored(),
            fieldWithPath("area").description("Area of real estate"),
            fieldWithPath("name").description("Name of real estate"),
            fieldWithPath("imageUrl").description("Image of real estate"),
            fieldWithPath("description").description("Description of real estate"),
            fieldWithPath("technicalEquipment").description("Technical equipment in real estate"),
            fieldWithPath("type").description("Type of real estate"),
            fieldWithPath("location").description("Location of real estate")
    };
}
