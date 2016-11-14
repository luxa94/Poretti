package com.bdzjn.poretti.service;


import com.bdzjn.poretti.model.Image;

import java.util.Optional;

public interface ImageService {

    Optional<Image> findById(long id);

}
