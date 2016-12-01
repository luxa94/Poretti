package com.bdzjn.poretti.controller.dto;

public class AdvertisementRealEstateDTO {

    private RealEstateDTO realEstateDTO;

    private AuthorizationDTO advertisementDTO;

    public RealEstateDTO getRealEstateDTO() {
        return realEstateDTO;
    }

    public void setRealEstateDTO(RealEstateDTO realEstateDTO) {
        this.realEstateDTO = realEstateDTO;
    }

    public AuthorizationDTO getAdvertisementDTO() {
        return advertisementDTO;
    }

    public void setAdvertisementDTO(AuthorizationDTO advertisementDTO) {
        this.advertisementDTO = advertisementDTO;
    }
    
}
