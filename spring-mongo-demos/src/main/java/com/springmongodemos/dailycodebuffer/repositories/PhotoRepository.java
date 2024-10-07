package com.springmongodemos.dailycodebuffer.repositories;

import com.springmongodemos.dailycodebuffer.collections.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
}
