package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.controller.criteria.AdvertisementSearchCriteria;
import com.bdzjn.poretti.model.Advertisement;
import com.bdzjn.poretti.model.QAdvertisement;
import com.bdzjn.poretti.model.enumeration.AdvertisementStatus;
import com.bdzjn.poretti.repository.AdvertisementRepositoryCustom;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class AdvertisementRepositoryImpl extends QueryDslRepositorySupport implements AdvertisementRepositoryCustom {

    public AdvertisementRepositoryImpl() {
        super(Advertisement.class);
    }

    @Override
    public Optional<Advertisement> findByIdAndOwnerId(long id, long ownerId) {
        final QAdvertisement advertisement = QAdvertisement.advertisement;
        final Advertisement result = from(advertisement)
                .where(advertisement.id.eq(id),
                        advertisement.advertiser.id.eq(ownerId))
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Advertisement> findReported() {
        final QAdvertisement advertisement = QAdvertisement.advertisement;
        return from(advertisement)
                .where(advertisement.reports.isNotEmpty())
                .fetch();
    }

    @Override
    public Page<Advertisement> findFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable) {
        final QAdvertisement advertisement = QAdvertisement.advertisement;
        final JPQLQuery<Advertisement> query = queryForCriteria(searchCriteria, advertisement)
                .where(advertisement.advertiser.id.eq(advertiserId));

        final long size = query.fetchCount();
        getQuerydsl().applyPagination(pageable, query);
        final List<Advertisement> result = query.fetch();

        return new PageImpl<>(result, pageable, size);
    }

    @Override
    public Page<Advertisement> findActiveFor(long advertiserId, AdvertisementSearchCriteria searchCriteria, Pageable pageable) {
        searchCriteria.setAdvertisementStatus(AdvertisementStatus.ACTIVE);
        final QAdvertisement advertisement = QAdvertisement.advertisement;
        final JPQLQuery<Advertisement> query = queryForCriteria(searchCriteria, advertisement)
                .where(advertisement.advertiser.id.eq(advertiserId));

        final long size = query.fetchCount();
        getQuerydsl().applyPagination(pageable, query);
        final List<Advertisement> result = query.fetch();

        return new PageImpl<>(result, pageable, size);
    }

    @Override
    public Page<Advertisement> findActive(AdvertisementSearchCriteria searchCriteria, Pageable pageable) {
        searchCriteria.setAdvertisementStatus(AdvertisementStatus.ACTIVE);
        final QAdvertisement advertisement = QAdvertisement.advertisement;
        final JPQLQuery<Advertisement> query = queryForCriteria(searchCriteria, advertisement);

        final long size = query.fetchCount();
        getQuerydsl().applyPagination(pageable, query);
        final List<Advertisement> result = query.fetch();

        return new PageImpl<>(result, pageable, size);
    }

    private JPQLQuery<Advertisement> queryForCriteria(AdvertisementSearchCriteria searchCriteria, QAdvertisement advertisement) {
        return from(advertisement)
                .where(searchCriteria.getRealEstateName() == null ? null : advertisement.realEstate.name.containsIgnoreCase(searchCriteria.getRealEstateName()),
                        searchCriteria.getAreaFrom() == null ? null : advertisement.realEstate.area.goe(searchCriteria.getAreaFrom()),
                        searchCriteria.getAreaTo() == null ? null : advertisement.realEstate.area.loe(searchCriteria.getAreaTo()),
                        searchCriteria.getCity() == null ? null : advertisement.realEstate.location.city.containsIgnoreCase(searchCriteria.getCity()),
                        searchCriteria.getCityArea() == null ? null : advertisement.realEstate.location.cityArea.containsIgnoreCase(searchCriteria.getCityArea()),
                        searchCriteria.getState() == null ? null : advertisement.realEstate.location.state.containsIgnoreCase(searchCriteria.getState()),
                        searchCriteria.getStreet() == null ? null : advertisement.realEstate.location.street.containsIgnoreCase(searchCriteria.getStreet()),
                        searchCriteria.getLatitude() == null ? null : advertisement.realEstate.location.latitude.between(searchCriteria.getLatitude() - 0.01, searchCriteria.getLatitude() + 0.01),
                        searchCriteria.getLongitude() == null ? null : advertisement.realEstate.location.longitude.between(searchCriteria.getLongitude() - 0.01, searchCriteria.getLongitude() + 0.01),
                        searchCriteria.getRealEstateType() == null ? null : advertisement.realEstate.type.eq(searchCriteria.getRealEstateType()),
                        searchCriteria.getAdvertisementTitle() == null ? null : advertisement.title.containsIgnoreCase(searchCriteria.getAdvertisementTitle()),
                        searchCriteria.getAdvertisementType() == null ? null : advertisement.type.eq(searchCriteria.getAdvertisementType()),
                        searchCriteria.getAdvertisementStatus() == null ? null : advertisement.status.eq(searchCriteria.getAdvertisementStatus()),
                        searchCriteria.getPriceFrom() == null ? null : advertisement.price.goe(searchCriteria.getPriceFrom()),
                        searchCriteria.getPriceTo() == null ? null : advertisement.price.loe(searchCriteria.getPriceTo()),
                        searchCriteria.getCurrency() == null ? null : advertisement.currency.eq(searchCriteria.getCurrency()));
    }

}
