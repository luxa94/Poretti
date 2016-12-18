package com.bdzjn.poretti.util.snippets;

import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class ReportSnippets {

    public static final FieldDescriptor[] AD_REPORT = new FieldDescriptor[]{
            fieldWithPath("description").description("Description of report for specified advertisement"),
            fieldWithPath("reason").description("Reason (one of predefined) for reporting specified advertisement.")
    };
}
