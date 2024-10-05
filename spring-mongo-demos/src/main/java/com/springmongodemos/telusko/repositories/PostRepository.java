package com.springmongodemos.telusko.repositories;

import com.springmongodemos.telusko.collections.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
