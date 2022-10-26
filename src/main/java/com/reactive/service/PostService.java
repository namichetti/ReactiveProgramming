package com.reactive.service;

import com.reactive.model.document.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

    Flux<Post> findAll();
    Mono<Post> save(Post post);
    Mono<Post> findById(String id);
    Mono<Post> update(Post post, String id);
    Mono<Void> deleteById(String id);
}
