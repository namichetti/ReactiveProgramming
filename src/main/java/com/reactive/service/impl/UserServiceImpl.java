package com.reactive.service.impl;

import com.reactive.model.document.User;
import com.reactive.repository.UserRepository;
import com.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WebClient webClient;

    @Override
    public Flux<User> findAll() {
        return this.webClient
                .get()
                .exchangeToFlux(r->r.bodyToFlux(User.class));

    }

    @Override
    public Mono<User> findById(String id) {
        var params = new HashMap<String,Object>();
        params.put("id",id);
        return this.webClient
                .get()
                .uri("/{id}",params)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<User> save(User user) {
        return this.webClient
                .post()
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<User> update(User user, String id) {
        return this.webClient
                .put()
                .uri("/{id}", Collections.singletonMap("id",id))
                .body(BodyInserters.fromValue(user))
                .retrieve()
                .bodyToMono(User.class);
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
