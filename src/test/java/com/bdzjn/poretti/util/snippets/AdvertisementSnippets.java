package com.bdzjn.poretti.util.snippets;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class AdvertisementSnippets {

    public static final ParameterDescriptor[] ADVERTISEMENT_ID = new ParameterDescriptor[]{
            parameterWithName("id").description("Id of advertisement")
    };

    public static final FieldDescriptor[] ADVERTISEMENT = new FieldDescriptor[]{
            fieldWithPath("id").description("Advertiser ID"),
            fieldWithPath("title").description("The advertisement title"),
            fieldWithPath("announcedOn").description("Date when advertisement is announced"),
            fieldWithPath("editedOn").description("Date when advertisement is edited"),
            fieldWithPath("endsOn").description("Date when advertisement is ending"),
            fieldWithPath("price").description("Price of real estate in advertisement"),
            fieldWithPath("currency").description("Currency of price"),
            fieldWithPath("advertiser").description("Advertiser of advertisement"),
            fieldWithPath("status").description("Status of advertisement"),
            fieldWithPath("type").description("Type of advertisement"),
            fieldWithPath("realEstate").description("Real estate which belongs to this advertisement"),
            fieldWithPath("averageRating").description("Average  rating of advertisements. Based on reviews")
    };

    public static final FieldDescriptor[] CREATE_ADVERTISEMENT_DTO = new FieldDescriptor[]{
            fieldWithPath("advertisementDTO.id").ignored(),
            fieldWithPath("advertisementDTO.title").description("Advertisement title"),
            fieldWithPath("advertisementDTO.price").description("Price of advertisement"),
            fieldWithPath("advertisementDTO.currency").description("Currency of price"),
            fieldWithPath("advertisementDTO.price").description("Price of advertisement"),
            fieldWithPath("advertisementDTO.type").description("Type of advertisement"),
            fieldWithPath("advertisementDTO.endsOn").description("Date when advertisement is ending")
    };

    public static final FieldDescriptor[] REAL_ESTATE_DTO = new FieldDescriptor[]{
            fieldWithPath("realEstateDTO.id").ignored(),
            fieldWithPath("realEstateDTO.area").description("Area of real estate"),
            fieldWithPath("realEstateDTO.name").description("Name of real estate"),
            fieldWithPath("realEstateDTO.imageUrl").description("Image of real estate"),
            fieldWithPath("realEstateDTO.description").description("Description of real estate"),
            fieldWithPath("realEstateDTO.technicalEquipment").description("Technical equipment in real estate"),
            fieldWithPath("realEstateDTO.type").description("Type of real estate"),
            fieldWithPath("realEstateDTO.location").description("Location of real estate"),
    };

    public static final FieldDescriptor[] ADVERTISEMENT_DTO = new FieldDescriptor[]{
            fieldWithPath("id").ignored(),
            fieldWithPath("price").description("Price of advertisement"),
            fieldWithPath("currency").description("Currency of price"),
            fieldWithPath("title").description("Title of advertisement"),
            fieldWithPath("endsOn").description("Date when advertisement ends"),
            fieldWithPath("type").description("Type of advertisement")
    };

    public static final RequestFieldsSnippet CREATE_REQUEST = requestFields(CREATE_ADVERTISEMENT_DTO).and(REAL_ESTATE_DTO);
}
