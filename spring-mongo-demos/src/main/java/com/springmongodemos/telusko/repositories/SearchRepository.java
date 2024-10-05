package com.springmongodemos.telusko.repositories;

import com.springmongodemos.telusko.collections.Post;

import java.util.List;

public interface SearchRepository {

    List<Post> findByText(String text);

}