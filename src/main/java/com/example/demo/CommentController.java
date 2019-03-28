package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class CommentController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	BlogRepository blogRepository;

	@GetMapping(path = "/comment/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Blog> feed() {
		return this.blogRepository.findAll();
	}

}