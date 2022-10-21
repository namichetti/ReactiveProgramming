package com.reactive.service.impl;

import com.reactive.model.document.User;
import com.reactive.repository.UserRepository;
import com.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Mono<User> save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public Mono<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return this.userRepository.deleteById(id);
    }
}
