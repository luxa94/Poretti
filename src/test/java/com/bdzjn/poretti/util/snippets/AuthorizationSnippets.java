package com.bdzjn.poretti.util.snippets;

import com.bdzjn.poretti.util.data.UserTestData;
import org.springframework.restdocs.headers.RequestHeadersSnippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;

public class AuthorizationSnippets {

    public static final RequestHeadersSnippet AUTH_HEADER = requestHeaders(
        headerWithName(UserTestData.AUTHORIZATION).description("Authentication token")
    );
}
