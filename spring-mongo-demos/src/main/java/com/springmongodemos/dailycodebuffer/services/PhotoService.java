package com.springmongodemos.dailycodebuffer.services;

import com.springmongodemos.dailycodebuffer.collections.Photo;
import com.springmongodemos.dailycodebuffer.repositories.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public String addPhoto(String originalFilename, MultipartFile image) throws IOException {
        Photo photo = new Photo()
                .setTitle(originalFilename)
                .setPhoto(new Binary(BsonBinarySubType.BINARY, image.getBytes()));

        return photoRepository.save(photo).getId();
    }

    public Photo getPhoto(String id) {
        return photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }
}
