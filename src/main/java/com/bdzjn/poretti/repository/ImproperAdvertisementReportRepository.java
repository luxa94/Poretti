package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.ImproperAdvertisementReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImproperAdvertisementReportRepository extends JpaRepository<ImproperAdvertisementReport, Long>,
                                                               ImproperAdvertisementReportRepositoryCustom {

    Optional<ImproperAdvertisementReport> findById(long id);

}
