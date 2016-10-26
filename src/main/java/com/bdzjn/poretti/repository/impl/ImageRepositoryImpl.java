package com.bdzjn.poretti.repository.impl;

import com.bdzjn.poretti.model.Image;
import com.bdzjn.poretti.repository.ImageRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

public class ImageRepositoryImpl extends QueryDslRepositorySupport implements ImageRepositoryCustom {

    public ImageRepositoryImpl() {
        super(Image.class);
    }

}
