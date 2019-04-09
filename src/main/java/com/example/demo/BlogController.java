package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

	private final ApplicationEventPublisher publisher;
	private final Flux<ProfileCreatedEvent> events;
	private final ObjectMapper objectMapper;

	public BlogController(ApplicationEventPublisher publisher, ProfileCreatedEventPublisher eventPublisher,
			ObjectMapper objectMapper) {
		this.publisher = publisher;
		this.events = Flux.create(eventPublisher).share();
		this.objectMapper = objectMapper;
	}

	@Autowired
	private BlogService blogService;

	@Autowired
	BlogRepository blogRepository;

	@GetMapping
	public Flux<Blog> findAll() {
		log.debug("findAll Blog");
		return blogService.findAll();
	}

	@GetMapping("/find")
	public Flux<Blog> findByTitle(@RequestParam String blogTitle) {
		log.debug("findByTitle Blog with blogTitle : {}", blogTitle);
		return blogService.findByTitle(blogTitle);
	}

	@GetMapping("/{id}")
	public Mono<Blog> findOne(@PathVariable String id) {
		log.debug("findOne Blog with id : {}", id);
		return blogService.findOne(id);
	}

	@PostMapping
	public Mono<Blog> create(@RequestBody Blog blog) {
		log.debug("create Blog with blog : {}", blog);
		return blogService.createBlog(blog)
				.doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)));
	}

	@DeleteMapping("/{id}")
	public Mono<Boolean> delete(@PathVariable String id) {
		log.debug("delete Blog with id : {}", id);
		return blogService.delete(id);
	}

	@PutMapping("/{id}")
	public Mono<Blog> updateBlog(@RequestBody Blog blog, @PathVariable String id) {
		log.debug("updateBlog Blog with id : {} and blog : {}", id, blog);
		return blogService.updateBlog(blog, id);
	}

	@GetMapping(value = "/save", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@CrossOrigin(origins = "http://localhost:8080")
	public Flux<String> saveAndSend() {
		return this.events.map(pce -> {
			try {
				return objectMapper.writeValueAsString(pce) + "\n\n";
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});
	}

}