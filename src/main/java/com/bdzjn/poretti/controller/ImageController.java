package com.bdzjn.poretti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    ServletContext servletContext;

    @PostMapping
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {

        try {
            String uuid = UUID.randomUUID().toString();
            final String imageName = file.getOriginalFilename();
            final String imageAbsoluteUrl = servletContext.getRealPath("/images") + "/" + uuid + "_" + imageName;
            final String imageRelativeUrl = "/images/" + uuid + "_" + imageName;

            final File newImage = new File(imageAbsoluteUrl);
            if (!newImage.exists()) {
                newImage.createNewFile();
            }
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(newImage));
            stream.write(file.getBytes());
            stream.close();
            return new ResponseEntity<>(imageRelativeUrl, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
