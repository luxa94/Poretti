package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long>, LocationRepositoryCustom {

    Optional<Location> findById(long id);

}
