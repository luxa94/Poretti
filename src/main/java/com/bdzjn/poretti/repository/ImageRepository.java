package com.bdzjn.poretti.repository;

import com.bdzjn.poretti.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findById(long id);

}
