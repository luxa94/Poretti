package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.repository.LocationRepositoryCustom;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class LocationRepositoryImpl extends QueryDslRepositorySupport implements LocationRepositoryCustom {

    public LocationRepositoryImpl() {
        super(Location.class);
    }

}
