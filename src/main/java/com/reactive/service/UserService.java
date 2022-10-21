package com.reactive.service;

import com.reactive.model.document.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<User> findAll();
    Mono<User> save(User user);
    Mono<User> findById(String id);
    Mono<Void> deleteById(String id);

}
