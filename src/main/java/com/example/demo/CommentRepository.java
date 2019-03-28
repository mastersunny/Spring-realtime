package com.example.demo;

import reactor.core.publisher.Flux;

public interface CommentRepository {

    Flux<Comment> findAll();

}
