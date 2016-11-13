package com.bdzjn.poretti.service.impl;

import com.bdzjn.poretti.model.Image;
import com.bdzjn.poretti.repository.ImageRepository;
import com.bdzjn.poretti.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Optional<Image> findById(long id) {
        return imageRepository.findById(id);
    }

}
