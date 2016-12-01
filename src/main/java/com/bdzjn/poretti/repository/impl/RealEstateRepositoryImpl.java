package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.QRealEstate;
import com.bdzjn.poretti.model.RealEstate;
import com.bdzjn.poretti.repository.RealEstateRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.Optional;

public class RealEstateRepositoryImpl extends QueryDslRepositorySupport implements RealEstateRepositoryCustom {

    public RealEstateRepositoryImpl() {
        super(RealEstate.class);
    }

    @Override
    public Optional<RealEstate> findByIdAndOwnerId(long realEstateId, long ownerId) {
        QRealEstate realEstate = QRealEstate.realEstate;
        final RealEstate result = from(realEstate).select(realEstate)
                .where(realEstate.id.eq(realEstateId),
                        realEstate.owner.id.eq(ownerId))
                .fetchFirst();
        return Optional.ofNullable(result);
    }
}
