package com.bdzjn.poretti.model.enumeration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum RealEstateType {
    APARTMENT,
    HOUSE,
    ROOM,
    COTTAGE,
    RESIDENTIAL_FACILITY(new RealEstateType[]{RealEstateType.APARTMENT, HOUSE, ROOM, COTTAGE}),
    BUSINESS_SPACE,
    WAREHOUSE,
    OUTLET,
    CATERING,
    MANUFACTURING,
    SPORT,
    BUSINESS_FACILITY(new RealEstateType[]{BUSINESS_SPACE, WAREHOUSE, OUTLET, CATERING, MANUFACTURING, SPORT}),
    CONSTRUCTION,
    AGRICULTURAL,
    LAND(new RealEstateType[]{CONSTRUCTION, AGRICULTURAL}),
    NEW_CONSTRUCTION;

    private final RealEstateType[] subTypes;

    RealEstateType(RealEstateType[] subTypes) {
        this.subTypes = subTypes;
    }

    RealEstateType() {
        subTypes = new RealEstateType[0];
    }

    public List<RealEstateType> getSubTypes() {
        return new ArrayList<>(Arrays.asList(subTypes));
    }

    public boolean hasSubtypes() {
        return subTypes.length > 0;
    }

}
