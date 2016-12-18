package com.bdzjn.poretti.util.snippets;

import com.bdzjn.poretti.util.data.UserTestData;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class AuthorizationSnippets {

    public static final RequestHeadersSnippet AUTH_HEADER = requestHeaders(
        headerWithName(UserTestData.AUTHORIZATION).description("Authentication token")
    );

    public static final FieldDescriptor[] LOGIN_DTO = new FieldDescriptor[]{
            fieldWithPath("username").description("Username of user"),
            fieldWithPath("password").description("Password of user"),
    };
}
