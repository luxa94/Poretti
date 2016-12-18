package com.bdzjn.poretti.util.snippets;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class UserSnippets {

    public static final ParameterDescriptor[] USER_ID = new ParameterDescriptor[]{
            parameterWithName("id").description("Id of user")
    };

    public static final FieldDescriptor[] REGISTER_DTO = new FieldDescriptor[]{
            fieldWithPath("email").description("Email of user"),
            fieldWithPath("username").description("Username of user"),
            fieldWithPath("password").description("Password of user"),
            fieldWithPath("name").description("Name of user"),
            fieldWithPath("imageUrl").ignored(),
            fieldWithPath("phoneNumbers").ignored(),
            fieldWithPath("contactEmails").ignored(),
            fieldWithPath("companyId").ignored()
    };

    public static final FieldDescriptor[] USER_DTO = new FieldDescriptor[]{
            fieldWithPath("name").description("Name of user"),
            fieldWithPath("imageUrl").description("Image of user"),
            fieldWithPath("phoneNumbers").description("Phone numbers of user"),
            fieldWithPath("contactEmails").description("Emails of user")
    };

    public static final FieldDescriptor[] USER = new FieldDescriptor[]{
            fieldWithPath("id").ignored(),
            fieldWithPath("username").description("Username of user"),
            fieldWithPath("email").description("Email of user"),
            fieldWithPath("name").description("Name of user"),
            fieldWithPath("role").description("User's roles"),
            fieldWithPath("registrationConfirmed").description("Status of user's account"),
            fieldWithPath("permissions").description("User's permissions"),
            fieldWithPath("phoneNumbers").description("Phone numbers of user"),
            fieldWithPath("contactEmails").description("Contact emails of user"),
            fieldWithPath("imageUrl").description("Image of user")
    };



}
