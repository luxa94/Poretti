package com.bdzjn.poretti.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterCompanyDTO {

    @JsonProperty("company")
    private CompanyDTO companyDTO;

    @JsonProperty("user")
    private RegisterDTO registerDTO;

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }

    public RegisterDTO getRegisterDTO() {
        return registerDTO;
    }

    public void setRegisterDTO(RegisterDTO registerDTO) {
        this.registerDTO = registerDTO;
    }
}
