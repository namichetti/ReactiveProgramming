package com.reactive.service.impl;

import com.reactive.model.document.Post;
import com.reactive.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private WebClient webClient;

    @Override
    public Flux<Post> findAll() {
        return this.webClient
                .get()
                .exchangeToFlux(r->r.bodyToFlux(Post.class));

    }

    @Override
    public Mono<Post> findById(String id) {
        var params = new HashMap<String,Object>();
        params.put("id",id);
        return this.webClient
                .get()
                .uri("/{id}",params)
                .retrieve()
                .bodyToMono(Post.class);
    }

    @Override
    public Mono<Post> save(Post post) {
        return this.webClient
                .post()
                .body(BodyInserters.fromValue(post))
                .retrieve()
                .bodyToMono(Post.class);
    }

    @Override
    public Mono<Post> update(Post post, String id) {
        return this.webClient
                .put()
                .uri("/{id}", Collections.singletonMap("id",id))
                .body(BodyInserters.fromValue(post))
                .retrieve()
                .bodyToMono(Post.class);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.webClient
                .delete()
                .uri("/{id}", Collections.singletonMap("id",id))
                .retrieve()
                .bodyToMono(Void.class);
    }
}
