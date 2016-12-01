package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.QAdvertisement;
import com.bdzjn.poretti.repository.AdvertisementRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.Optional;

public class AdvertisementRepositoryImpl extends QueryDslRepositorySupport implements AdvertisementRepositoryCustom {

    public AdvertisementRepositoryImpl() {
        super(Advertisement.class);
    }

    @Override
    public Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId) {
        QAdvertisement advertisement = QAdvertisement.advertisement;
        final Advertisement result = from(advertisement).select(advertisement)
                .where(advertisement.id.eq(id),
                        advertisement.advertiser.id.eq(ownerId))
                .fetchFirst();

        return Optional.ofNullable(result);
    }
}
