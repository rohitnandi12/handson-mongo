package com.springmongodemos.telusko.controller;

import com.springmongodemos.telusko.collections.Post;
import com.springmongodemos.telusko.repositories.PostRepository;
import com.springmongodemos.telusko.repositories.SearchRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final SearchRepository searchRepository;

    public PostController(
            PostRepository postRepository,
            SearchRepository searchRepository
    ) {
        this.postRepository = postRepository;
        this.searchRepository = searchRepository;
    }


    @GetMapping
    public List<Post> posts() {
        return postRepository.findAll();
    }

    @PostMapping
    public Post addPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping("/search")
    public List<Post> posts(@RequestParam String query) {
        return searchRepository.findByText(query);
    }

}
