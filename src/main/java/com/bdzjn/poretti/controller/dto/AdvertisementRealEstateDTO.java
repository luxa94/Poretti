package com.bdzjn.poretti.controller.dto;

public class AdvertisementRealEstateDTO {

    private RealEstateDTO realEstateDTO;

    private AdvertisementDTO advertisementDTO;

    public RealEstateDTO getRealEstateDTO() {
        return realEstateDTO;
    }

    public void setRealEstateDTO(RealEstateDTO realEstateDTO) {
        this.realEstateDTO = realEstateDTO;
    }

    public AdvertisementDTO getAdvertisementDTO() {
        return advertisementDTO;
    }

    public void setAdvertisementDTO(AdvertisementDTO advertisementDTO) {
        this.advertisementDTO = advertisementDTO;
    }
    
}
